package com.yi.cloud.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yi.cloud.common.exception.ServiceException;
import com.yi.cloud.common.util.MD5Util;
import com.yi.cloud.jwt.utils.JwtTokenUtil;
import com.yi.cloud.model.auth.ILoginUser;
import com.yi.cloud.system.api.context.LoginUser;
import com.yi.cloud.system.api.exception.enums.AuthExceptionEnum;
import com.yi.cloud.system.constants.SystemConstants;
import com.yi.cloud.system.entity.SysUser;
import com.yi.cloud.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 用户服务实现类
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户登录，返回token
     *
     * @param userName 用户名称
     * @param password 用户密码
     * @return token
     */
    public String login(String userName, String password) {

        //1.查询账号是否存在系统
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, userName);
        List<SysUser> userList = this.list(queryWrapper);
        if (userList.isEmpty()) {
            //用户不存在
            throw new ServiceException(AuthExceptionEnum.USER_NOT_FOUND);
        } else {
            //2.校验密码是否正确
            SysUser sysUser = userList.get(0);
            String md5 = MD5Util.encrypt(password);
            if (!md5.equals(sysUser.getPassword())) {
                throw new ServiceException(AuthExceptionEnum.INVALID_PWD);
            }

            //3.生成token
            String token = jwtTokenUtil.generateToken(sysUser.getAccountId().toString(), null);

            //4.缓存token TODO
            LoginUser loginUser = new LoginUser();
            loginUser.setAccountId(sysUser.getAccountId());
            BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(SystemConstants.LOGIN_USER_CACHE_PREFIX + token);
            opts.set(loginUser, SystemConstants.DEFAULT_LOGIN_TIME_OUT_SECS, TimeUnit.SECONDS);
            return token;
        }
    }

    /**
     * 校验token
     *
     * @param token 登录令牌
     * @return true or false
     */
    public boolean checkToken(String token) {
        //校验是否正确
        return jwtTokenUtil.checkToken(token);

        //TODO 校验缓存是否存在当前token
    }

    /**
     * 退出登录
     *
     * @param token 登录令牌
     */
    public void logout(String token) {

        //从缓存删除token TODO
    }

    /**
     * 通过token 获取用户令牌
     * @param token 登录令牌
     * @return 用户信息
     */
    public ILoginUser getLoginUserByToken(String token) {
        //TODO 从redis 获取用户信息
        return null;
    }
}
