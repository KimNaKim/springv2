package com.metacoding.springv2.board;

import lombok.Data;

public class BoardRequest {
    
    @Data
    public static class SaveDTO {
        private String title;
        private String content;

        public Board toEntity(com.metacoding.springv2.user.User user) {
            return Board.builder()
                    .title(this.title)
                    .content(this.content)
                    .user(user)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {
        private String title;
        private String content;
    }
}
