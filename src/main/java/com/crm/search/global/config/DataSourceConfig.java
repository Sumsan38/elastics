package com.crm.search.global.config;

import com.crm.search.global.datasource.DataSourceType;
import com.crm.search.global.datasource.RoutingDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * 해당 config에서는 DataSource를 등록한다.
 * 여기서 DataSource는 Transaction 요청 종류에 따라 READ, WRITE별 DataSource를 스위칭해서 반환한다. (JPA 전용)
 * Mybatis의 경우에는 @Transaction이 열리기 전에 getConnection을 타기 때문에 해당 분기를 타지 않음
 * -애플리케이션 시작 시
 * 1. yml 로딩
 * 2. DataSourceProperties에 값 바인딩
 * 3. writeDataSource 생성
 * 4. readDataSource 생성
 * 5. routingDataSource 생성
 * 6. routingDataSource가 @Primary로 등록
 * - 요청 발생 시
 * 1. @Transactional 시작
 * 2. readOnly 여부 ThreadLocal에 저장
 * 3. routingDataSource.getConnection() 호출
 * 4. determineCurrentLookupKey() 실행 (AbstractRoutingDataSource 상속 받은 RoutingDataSource 참고)
 * 5. Map에서 DataSource 선택
 * 6. 실제 DB 연결 수행
 */
@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.write")
    public DataSourceProperties writeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource writeDataSource() {
        /*
        * - initializeDataSourceBuilder에서 하는 일
        * DriverClassName 확인
        * HikariDataSource 생성
        * url / username / password 설정
        * 실제 DataSource 객체 생성
        * */
        return writeDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.read")
    public DataSourceProperties readDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource readDataSource() {
        return readDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    // DataSource를 필요로 하는 모든 요청이 routingDataSource로 온다. (@Primary)
    // Spring이 DataSource로 사용하면서 getConnection을 호출할 때, AbstractRoutingDataSource.determineCurrentLookupKey() 호출
    // 그때 등록한 targetDataSource Map의 Key, Value를 통해 write, read DataSource 호출
    @Primary
    @Bean
    public DataSource routingDataSource(
            DataSource writeDataSource,
            DataSource readDataSource) {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        HashMap<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceType.WRITE, writeDataSource);
        targetDataSource.put(DataSourceType.READ, readDataSource);

        routingDataSource.setTargetDataSources(targetDataSource);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

}
