package com.crm.search.global.config;

import com.crm.search.global.interceptor.MyBatisReadOnlyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MtBatisPluginConfig {

    @Bean
    public MyBatisReadOnlyInterceptor myBatisReadOnlyInterceptor() {
        return new MyBatisReadOnlyInterceptor();
    }

}
