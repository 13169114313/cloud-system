package com.yi.cloud.system.provider;

import com.yi.cloud.system.api.context.LoginUser;
import com.yi.cloud.system.api.service.ApiAuthService;
import com.yi.cloud.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权服务的提供者
 *
 * @author chenguoyi
 */
@Primary
@RestController
public class AuthServiceProviderImpl implements ApiAuthService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public String login(String account, String password) {
        return sysUserService.login(account, password);
    }

    @Override
    public boolean checkToken(String token) {
        return sysUserService.checkToken(token);
    }

    @Override
    public void logout(String token) {
        sysUserService.logout(token);
    }

    @Override
    public LoginUser getLoginUserByToken(String token) {
        return sysUserService.getLoginUserByToken(token);
    }
}
