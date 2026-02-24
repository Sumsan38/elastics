package com.crm.search.domain._testsupport.routing;

import com.crm.search.infra.mybatis.mapper.TestMyBatisMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Import(MyBatisTestDbInitConfig.class)
public class MyBatisRoutingDataSourceTest {

    @Autowired
    private TestMyBatisMapper testMyBatisMapper;

    @Test
    @Transactional(readOnly = true)
    void mybatisSelect_shouldRouteToReadDataSource() {
        String url = testMyBatisMapper.selectJdbcUrl();

        assertThat(url).isNotNull();
        assertThat(url).contains("readDb");
    }

    @Test
    @Transactional(readOnly = true)
    void mybatisInsert_shouldFail_whenTransactionISReadOnly(){
        // readOnly 트랜잭션에서 INSERT가 interceptor로 차단되는지 확인
        assertThatThrownBy(() -> testMyBatisMapper.insertName("BLOCK")).isInstanceOf(Exception.class);
    }


}
