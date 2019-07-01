package com.yi.cloud.system.provider;

import com.netflix.ribbon.proxy.annotation.Http;
import com.yi.cloud.system.api.dto.SysUserDto;
import com.yi.cloud.system.api.service.ApiUserService;
import com.yi.cloud.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author chenguoyi
 */
@RestController
public class UserServiceProviderImpl implements ApiUserService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public boolean addUser(@RequestBody SysUserDto sysUserDto) {

        return sysUserService.addUser(sysUserDto);
    }
}
