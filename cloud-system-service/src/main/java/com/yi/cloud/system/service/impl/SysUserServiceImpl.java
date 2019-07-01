package com.yi.cloud.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yi.cloud.common.exception.ServiceException;
import com.yi.cloud.common.util.MD5Util;
import com.yi.cloud.jwt.utils.JwtTokenUtil;
import com.yi.cloud.system.api.context.LoginUser;
import com.yi.cloud.system.api.dto.SysUserDto;
import com.yi.cloud.system.api.exception.enums.AuthExceptionEnum;
import com.yi.cloud.system.constants.SystemConstants;
import com.yi.cloud.system.entity.SysUser;
import com.yi.cloud.system.mapper.SysUserMapper;
import com.yi.cloud.system.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 用户服务实现类
 *
 * @author chenguoyi
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

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
    @Override
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

            //4.缓存token
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
    @Override
    public boolean checkToken(String token) {
        //校验是否正确
        if (!jwtTokenUtil.checkToken(token)) {
            return false;
        }

        //校验缓存是否存在当前token
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(SystemConstants.LOGIN_USER_CACHE_PREFIX + token);
        LoginUser loginUser = (LoginUser) opts.get();

        return null != loginUser;
    }

    /**
     * 退出登录
     *
     * @param token 登录令牌
     */
    @Override
    public void logout(String token) {

        //从缓存删除token
        redisTemplate.delete(SystemConstants.LOGIN_USER_CACHE_PREFIX + token);
    }

    /**
     * 通过token 获取用户令牌
     *
     * @param token 登录令牌
     * @return 用户信息
     */
    @Override
    public LoginUser getLoginUserByToken(String token) {
        //从redis 获取用户信息
        BoundValueOperations<String, Object> opts = redisTemplate.boundValueOps(SystemConstants.LOGIN_USER_CACHE_PREFIX + token);
        Object loginUser = opts.get();

        return (null != loginUser) ? (LoginUser) loginUser : null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean addUser(SysUserDto userDto) {
        //校验是否已存在当前账号 TODO

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);

        //保存用户
        return this.save(sysUser);
    }
}
