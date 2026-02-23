package com.metacoding.springv2.user;

import lombok.Data;

public class UserRequest {

    @Data
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;

        public User toEntity(String encPassword) {
            return User.builder()
                    .username(username)
                    .password(encPassword)
                    .email(email)
                    .roles("USER")
                    .build();
        }
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }
}