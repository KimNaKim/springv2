package com.metacoding.springv2.board;

import java.sql.Timestamp;

import lombok.Data;

public class BoardResponse {

    // 1. 게시글 목록 조회를 위한 DTO
    @Data
    public static class BoardDTO {
        private Integer id;
        private String title;
        private String year;

        BoardDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.year = board.getCreatedAt().toString().substring(0, 4);
        }
    }

    // 2. 게시글 상세보기를 위한 DTO
    @Data
    public static class DetailDTO {
        private Integer id;
        private String title;
        private String content;
        private Integer userId;
        private String username;
        private Boolean isBoardOwner;

        DetailDTO(Board board, int sessionUserId) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername(); // Lazy Loading 발동
            this.isBoardOwner = sessionUserId == board.getUser().getId();
        }
    }

    // 3. 삽입 확인을 위한 DTO
    @Data
    public static class SaveDTO {
        private Integer id;
        private String title;
        private String createdAt;

        SaveDTO(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.createdAt = board.getCreatedAt().toString();
        }
    }

}
