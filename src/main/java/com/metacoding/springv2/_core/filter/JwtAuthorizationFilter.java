package com.metacoding.springv2._core.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.metacoding.springv2._core.util.JwtUtil;
import com.metacoding.springv2.user.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 인가 필터
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // localhost:8080/api/good
        // header -> Authorization : Bearer JWT토큰

        String jwt = request.getHeader("Authorization");

        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FyIiwicm9sZXMiOiJVU0VSIiwiaWQiOjEsImV4cCI6MTc3MjA5Mjc5MX0.y8bOue7RpV8HZZ4I5Evd4FMmWMcnFr5iZi4NRinCZhCBfwuNcEOXjKSwKUF5yaeUui-fLdRhwGdJABr6DbjfNQ
        jwt = jwt.replace("Bearer ", "");

        // id, username, roles 받기
        User user = JwtUtil.verify(jwt);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

}