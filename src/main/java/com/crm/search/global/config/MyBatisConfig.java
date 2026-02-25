package com.crm.search.global.config;

import com.crm.search.global.interceptor.MyBatisReadOnlyInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = "com.crm.search.infra.mybatis.mapper",
        sqlSessionFactoryRef = "sqlSessionFactory"
)
public class MyBatisConfig {

    private final DataSource dataSource;
    private final MyBatisReadOnlyInterceptor interceptor;

    public MyBatisConfig(
            @Qualifier("readDataSource") DataSource dataSource,
            MyBatisReadOnlyInterceptor interceptor) {
        this.dataSource = dataSource;
        this.interceptor = interceptor;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml")
        );

        factoryBean.setPlugins(interceptor);

        return factoryBean.getObject();
    }
}
