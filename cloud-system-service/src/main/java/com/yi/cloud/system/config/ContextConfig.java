package com.yi.cloud.system.config;

import com.yi.cloud.model.api.IApiAuthService;
import com.yi.cloud.system.api.context.LoginContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 全局配置
 */
@Configuration
public class ContextConfig {

    /**
     * 获取当前用户的便捷工具
     */
    @Bean
    public LoginContext loginContext(IApiAuthService authService) {
        return new LoginContext(authService);
    }

//    /**
//     * 调用链治理
//     */
//    @Bean
//    public ChainOnConsumerAop chainOnConsumerAop() {
//        return new ChainOnConsumerAop();
//    }
//
//    /**
//     * 调用链治理
//     */
//    @Bean
//    public ChainOnControllerAop chainOnControllerAop() {
//        return new ChainOnControllerAop();
//    }
//
//    /**
//     * 调用链治理
//     */
//    @Bean
//    public ChainOnProviderAop chainOnProviderAop() {
//        return new ChainOnProviderAop();
//    }

}
