package com.nursy.nursy.config;

import com.nursy.nursy.jwt.JwtTokenFilter;
import com.nursy.nursy.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    // 모든 요청에 대해 접근을 허용하는 SecurityFilterChain 빈
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //jwtTokenFilter.setStr("jwt일때");
        http
                .csrf().disable()  // CSRF 보호 비활성화 (개발용)
                .addFilterBefore(new JwtTokenFilter(jwtTokenUtil,userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/**").permitAll()  // 모든 경로에 대해 접근 허용
                .anyRequest().authenticated();  // 나머지 요청은 인증된 사용자만 접근

        return http.build();
    }
}
