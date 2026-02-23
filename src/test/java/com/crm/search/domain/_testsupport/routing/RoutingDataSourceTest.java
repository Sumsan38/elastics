package com.crm.search.domain._testsupport.routing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class RoutingDataSourceTest {

    @Autowired
    private TestEntityRepository testEntityRepository;

    @Autowired
    private DataSource routingDataSource;

    @Test
    @Transactional
    void writeTransaction_shouldUseWriteDb() throws Exception {
        testEntityRepository.save(new TestEntity("WRITE_TEST"));

        Connection connection = routingDataSource.getConnection();
        String url = connection.getMetaData().getURL();

        assertThat(url.contains("writeDb")).isTrue();
    }

    @Test
    @Transactional(readOnly = true)
    void readTransaction_shouldUseReadDb() throws Exception {
        testEntityRepository.findAll();

        Connection connection = routingDataSource.getConnection();
        String url = connection.getMetaData().getURL();

        assertThat(url.contains("readDb")).isTrue();
    }
}
