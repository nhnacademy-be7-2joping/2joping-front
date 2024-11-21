package com.nhnacademy.twojopingfront.common.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    private final ElasticConfig elasticConfig;

    public ElasticsearchConfig(ElasticConfig elasticConfig) {
        this.elasticConfig = elasticConfig;
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        String elasticUrl = elasticConfig.getUrl(); // URL 가져오기
        String bookIndexName = elasticConfig.getBookIndexName(); // 인덱스명 가져오기

        // 인증 설정
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "nhnacademy123!"));

        // RestClient 설정
        RestClientBuilder builder = RestClient.builder(new HttpHost(elasticUrl, 9200, "http"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        RestClient restClient = builder.build();

        // ElasticsearchClient 생성
        RestClientTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        return new ElasticsearchClient(transport);
    }
}

