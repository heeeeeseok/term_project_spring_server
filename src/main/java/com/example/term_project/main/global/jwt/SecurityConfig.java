package com.example.term_project.main.global.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] PERMITTED_URLS = {
            "/user/signup", //회원가입
            "/user/login", //로그인
            "/user/test",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .cors()//CORS 허용 정책(Front , Back 사이에 도메인이 달라지는 경우)
                .and()
                .csrf().disable() // //서버에 인증정보를 저장하지 않기 때문에 굳이 불필요한 csrf 코드들을 작성할 필요가 없다.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //스프링시큐리티가 세션을 생성하지도않고 기존것을 사용하지도 않음 ->JWT 같은토큰방식을 쓸때 사용하는 설정
                .and()
                .formLogin().disable()  // 서버에서  View를 배포하지 않으므로 disable
                .httpBasic().disable() // JWT 인증 방식을 사용하기에 httpBasic을 이용한 인증방식 사용 안함
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)//필터 추가
                .authorizeHttpRequests(ant -> ant
                        .antMatchers(PERMITTED_URLS).permitAll() // 해당 문자열 배열에 저장된 uri 요청은 제외
                        .anyRequest().authenticated() // 모든 요청은 Auth 받아야함
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

    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }
}
