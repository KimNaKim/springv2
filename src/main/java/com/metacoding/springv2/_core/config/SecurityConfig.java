package com.metacoding.springv2._core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.metacoding.springv2._core.filter.CorsFilter;
import com.metacoding.springv2._core.filter.JwtAuthorizationFilter;
import com.metacoding.springv2._core.util.RespFilter;

@Configuration
public class SecurityConfig {

        // 인코더 IOC에 등록
        @Bean
        public BCryptPasswordEncoder encode() {
                return new BCryptPasswordEncoder();
        }

        // 시큐리티 필터 등록
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http.headers(headers -> headers
                                .frameOptions(frameOptions -> frameOptions.sameOrigin()));

                // http.addFilter(new CorsFilter());

                http.exceptionHandling(ex -> ex
                                .authenticationEntryPoint(
                                                (request, response, authException) -> RespFilter.fail(response, 401,
                                                                "로그인 후 이용해주세요"))
                                .accessDeniedHandler(
                                                (request, response, accessDeniedException) -> RespFilter.fail(response,
                                                                403, "권한이 없습니다")));

                // jsessionID는 요청이 끝나면 사라짐 (stateless서버화) - 세션이 존재하는 도중에는 jsessionID을 계속 사용함
                http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                // 인증/권한 주소 커스터마이징
                http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/api/check-username").permitAll() // 중복체크는 로그인 없이 가능
                                .requestMatchers("/api/**").authenticated() // 들어갈 때는 인증(로그인)이 필요
                                .requestMatchers("/admin/**").hasAnyRole("ADMIN") // 들어갈 때는 권한체크가 필요
                                .anyRequest().permitAll()); // 람다를 넣어야함

                // form 로그인 비활성화 (Post : x-www-form-urlencoded : username, password)
                http.formLogin(f -> f.disable());

                // 베이직 인증 활성화 시킴(request할때마다 username,password를 요구)
                http.httpBasic(b -> b.disable());

                // input에 csrf 토큰 받기를 비활성화하기
                http.csrf(c -> c.disable());

                // 인증 필터를 변경
                http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}