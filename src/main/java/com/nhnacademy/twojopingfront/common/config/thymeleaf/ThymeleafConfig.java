package com.nhnacademy.twojopingfront.common.config.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * ThymeleafConfig
 *
 * Thymeleaf와 관련된 설정을 정의하는 구성 클래스.
 * 사용자 정의 Formatter와 같은 Thymeleaf 관련 Bean을 등록합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Configuration
public class ThymeleafConfig {

    @Bean
    public ThymeleafCustomFormatter customFormatter() {
        return new ThymeleafCustomFormatter();
    }
}