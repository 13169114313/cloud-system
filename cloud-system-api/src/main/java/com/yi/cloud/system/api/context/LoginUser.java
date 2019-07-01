package com.yi.cloud.system.api.context;


import com.yi.cloud.model.context.AbstractLoginUser;

import java.util.Set;


/**
 * 当前用户的登录信息
 * @author chenguoyi
 */
@SuppressWarnings("ALL")
public class LoginUser implements AbstractLoginUser {

    /**
     * 账号id
     */
    private Long accountId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 角色id集合
     */
    private Set<Long> roleIds;

    /**
     * 角色编码集合
     */
    private Set<Long> roleCodes;

    /**
     * 可用资源集合
     */
    private Set<Long> resourceUrls;

    @Override
    public Long getUserUniqueId() {
        return accountId;
    }

    @Override
    public Long getAppId() {
        return appId;
    }

    @Override
    public Set<Long> getRoleIds() {
        return roleIds;
    }

    @Override
    public Set<Long> getRoleCodes() {
        return roleCodes;
    }

    @Override
    public Set<Long> getResourceUrls() {
        return resourceUrls;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public void setRoleCodes(Set<Long> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public void setResourceUrls(Set<Long> resourceUrls) {
        this.resourceUrls = resourceUrls;
    }
}
