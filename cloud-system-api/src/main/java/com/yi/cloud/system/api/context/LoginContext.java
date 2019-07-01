package com.yi.cloud.system.api.context;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yi.cloud.common.constants.CloudConstants;
import com.yi.cloud.common.exception.CoreExceptionEnum;
import com.yi.cloud.common.exception.ServiceException;
import com.yi.cloud.core.utils.HttpContext;
import com.yi.cloud.core.utils.SpringContextHolder;
import com.yi.cloud.model.api.IApiAuthService;
import com.yi.cloud.model.context.AbstractLoginContext;
import com.yi.cloud.model.context.AbstractLoginUser;
import com.yi.cloud.model.context.LoginUserHolder;
import com.yi.cloud.system.api.service.ApiAuthService;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录信息上下文
 * @author chenguoyi
 */
public class LoginContext implements AbstractLoginContext {

    private ApiAuthService authService;

    public LoginContext(ApiAuthService authService) {
        this.authService = authService;
    }

    public static LoginContext get() {
        return SpringContextHolder.getBean(LoginContext.class);
    }

    /**
     * 获取当前用户的token
     * <p>
     * 先判断header中是否有Authorization字段，
     * 如果header中没有这个字段，则检查请求参数中是否带token，
     * 如果任意一个地方有这个值，则返回这个值
     * 两个地方都没有token，则抛出没有登录用户异常
     */
    @Override
    public String getCurrentUserToken() {
        HttpServletRequest request = HttpContext.getRequest();
        if (null == request) {
            throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
        }

        //如果请求是在http环境下，则有request对象
        String authorization = request.getHeader(CloudConstants.AUTH_HEADER);
        if (StringUtils.isNotEmpty(authorization)) {
            return authorization;
        }

        //从参数获取
        String token = request.getParameter(CloudConstants.TOKEN);
        if (StringUtils.isNotEmpty(token)) {
            return token;

        }

        throw new ServiceException(CoreExceptionEnum.NO_CURRENT_USER);
    }

    /**
     * 获取当前用户
     * <p>
     * 先从ThreadLocal中拿user，如果有值就直接返回，没取到再去调用远程服务,调用完远程服务把获取到的user放到Threadlocal里
     */
    @Override
    public <T extends AbstractLoginUser> T getLoginUser() {
        AbstractLoginUser currentUser = LoginUserHolder.get();
        if (null != currentUser) {
            return (T) currentUser;
        }

        String token = getCurrentUserToken();
        return (T) this.authService.getLoginUserByToken(token);
    }

    /**
     * 获取当前登录用户的账户id
     */
    public Long getUserAccountId() {
        AbstractLoginUser abstractLoginUser = this.getLoginUser();

        return (null == abstractLoginUser) ? null : abstractLoginUser.getUserUniqueId();
    }
}
