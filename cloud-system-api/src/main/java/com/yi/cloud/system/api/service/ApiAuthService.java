package com.yi.cloud.system.api.service;

import com.yi.cloud.model.auth.ILoginUser;
import com.yi.cloud.model.context.AbstractLoginUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 鉴权服务,提供颁发,校验,注销等方法
 *
 * @author chenguoyi
 */
@RequestMapping("api/authService")
public interface ApiAuthService {

    /**
     * <p>登录(验证账号密码)</p>
     * <p>若登录成功则返回token,若登录不成功则返回null</p>
     *
     * @param account  账号
     * @param password 密码
     * @return token
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String login(@RequestParam("account") String account, @RequestParam("password") String password);

    /**
     * 校验token
     *
     * @param token token
     * @return true:校验成功,false:校验失败
     */
    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    boolean checkToken(@RequestParam("token") String token);

    /**
     * 注销token
     *
     * @param token token
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    void logout(@RequestParam("token") String token);

    /**
     * 通过token获取用户信息
     *
     * @param token token
     * @return 当前从token用户信息
     */
    @RequestMapping(value = "/getLoginUserByToken", method = RequestMethod.POST)
    AbstractLoginUser getLoginUserByToken(@RequestParam("token") String token);
}
