package com.nhnacademy.twojopingfront.common.config;

import com.nhnacademy.twojopingfront.common.interceptor.AdminCheckInterceptor;
import com.nhnacademy.twojopingfront.common.interceptor.ApplyMemberInfoInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApplyMemberInfoInterceptor());
        registry.addInterceptor(new AdminCheckInterceptor()).excludePathPatterns("/admin/**", "/css/**", "/js/**", "/images/**", "/fonts/**");
    }
}
