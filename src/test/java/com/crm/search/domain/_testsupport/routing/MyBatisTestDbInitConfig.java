package com.crm.search.domain._testsupport.routing;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@TestConfiguration
public class MyBatisTestDbInitConfig {

    @Bean
    ApplicationRunner initMyBatisTestTable(
            DataSource writeDataSource,
            DataSource readDataSource
    ) {
        return args -> {

        };
    }

    private void createTable(DataSource dataSource) throws Exception{
        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()
        ) {
            st.execute("""
                    CREATE TABLE IF NOT EXISTS MYBATIS_TEST (
                        ID BIGINT PRIMARY KEY AUTO_INCREMENT,
                        NAME VARCHAR(255) NOT NULL
                        )
                    """);
        }
    }
}
