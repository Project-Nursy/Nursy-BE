package com.nursy.nursy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // 모든 요청에 대해 접근을 허용하는 SecurityFilterChain 빈
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // CSRF 보호 비활성화 (개발용)
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()  // 모든 경로에 대해 접근 허용
                .anyRequest().authenticated();  // 나머지 요청은 인증된 사용자만 접근

        return http.build();
    }
}
