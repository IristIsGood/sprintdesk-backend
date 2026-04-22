package com.sprintdesk.sprintdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // 允许前端域名访问     // 本地开发
        config.addAllowedOrigin("http://localhost:3000");
            
        // EC2 前端
        config.addAllowedOrigin("http://54.254.226.187");

        // 允许所有 HTTP 方法
        config.addAllowedMethod("*");

        // 允许所有 Header，包括 Authorization
        config.addAllowedHeader("*");

        // 允许携带认证信息
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}