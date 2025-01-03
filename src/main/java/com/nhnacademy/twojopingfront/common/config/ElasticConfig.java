package com.nhnacademy.twojopingfront.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "elastic")
public class ElasticConfig {
    private String url;
    private String bookIndexName;
}