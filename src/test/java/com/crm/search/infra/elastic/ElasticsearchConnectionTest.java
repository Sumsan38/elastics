package com.crm.search.infra.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.InfoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ElasticsearchConnectionTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchClient client;

    @Test
    void shouldConnectToElasticsearch(){
        assertThat(elasticsearchTemplate).isNotNull();
    }

    @Test
    void shouldConnectToCluster() throws Exception {
        InfoResponse info = client.info();
        assertThat(info.clusterName()).isNotBlank();
    }
}
