package com.metacoding.springv2.reply;

import lombok.Data;

public class ReplyRequest {
    
    @Data
    public static class SaveDTO {
        private String comment;
        private Integer boardId;

        public Reply toEntity(com.metacoding.springv2.user.User user, com.metacoding.springv2.board.Board board) {
            return Reply.builder()
                    .comment(this.comment)
                    .user(user)
                    .board(board)
                    .build();
        }
    }
}
