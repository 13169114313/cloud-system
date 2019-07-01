package com.yi.cloud.system.api.service;

import com.yi.cloud.system.api.dto.SysUserDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务远程调用接口
 * @author chenguoyi
 */
@RequestMapping("api/userService")
public interface ApiUserService {

    /**
     * 添加用户
     *
     * @param sysUserDto 用户对象
     * @return true or false
     */
    @RequestMapping(value = "/add/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    boolean addUser(SysUserDto sysUserDto);
}
