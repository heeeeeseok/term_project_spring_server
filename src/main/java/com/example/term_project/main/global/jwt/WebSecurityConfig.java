package com.example.term_project.main.global.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] PERMITTED_URLS = {
            "/", // 홈
            "/user/signup", //회원가입
            "/user/login", //로그인
            "/user/test",
    };

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                // csrf 비활성화
                .csrf().disable()
                // 세션을 활용하지 않으므로 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // form 기반의 로그인에 대해 비활성화 한다.
                .formLogin().disable()
                // UsernamePasswordAuthenticationFilter 이후에 필터 적용
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(ant -> ant
                        .antMatchers(PERMITTED_URLS).permitAll() // 명시된 Url 허용
                        .anyRequest().authenticated() // 이외의 모든 요청은 Auth 필요
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        return new JwtAuthenticationFilter(jwtTokenProvider);
    }
}
