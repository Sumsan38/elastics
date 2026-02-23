package com.crm.search.global.config;

import com.crm.search.global.interceptor.MyBatisReadOnlyInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MtBatisPluginConfig {

    @Bean
    public MyBatisReadOnlyInterceptor myBatisReadOnlyInterceptor() {
        return new MyBatisReadOnlyInterceptor();
    }

    @Bean
    public ConfigurationCustomizer myBatisConfigurationCustomizer(
            MyBatisReadOnlyInterceptor interceptor
    ) {
        return configuration -> configuration.addInterceptor(interceptor);
    }
}
