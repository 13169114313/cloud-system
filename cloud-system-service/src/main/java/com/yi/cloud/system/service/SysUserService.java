package com.yi.cloud.system.service;

import com.yi.cloud.system.api.context.LoginUser;
import com.yi.cloud.system.api.dto.SysUserDto;

/**
 * 用户服务接口
 *
 * @author chenguoyi
 */
public interface SysUserService {
    /**
     * 用户登录接口，获取令牌
     *
     * @param account  账号ID
     * @param password 密码
     * @return 登录令牌
     */
    String login(String account, String password);

    /**
     * 校验令牌令牌
     *
     * @param token 令牌
     * @return true or false
     */
    boolean checkToken(String token);

    /**
     * 登出接口
     *
     * @param token 令牌
     */
    void logout(String token);

    /**
     * 通过令牌获取用户信息
     *
     * @param token 令牌
     * @return 用户信息
     */
    LoginUser getLoginUserByToken(String token);

    /**
     * 添加系统用户
     *
     * @param userDto 用户信息传输对象
     * @return true or false
     */
    boolean addUser(SysUserDto userDto);

}
