package com.metacoding.springv2.auth;

import com.metacoding.springv2.user.User;

import lombok.Data;

public class AuthResponse {

    // 회원가입 시 추가된 정보를 응답하기 위해 만드는 DTO
    @Data
    public static class DTO {
        private Integer id;
        private String username;
        private String roles;

        public DTO(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.roles = user.getRoles();
        }

    }
}