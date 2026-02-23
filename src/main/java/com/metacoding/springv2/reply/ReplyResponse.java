package com.metacoding.springv2.reply;

import java.sql.Timestamp;
import lombok.Data;

public class ReplyResponse {

    @Data
    public static class DTO {
        private Integer id;
        private String comment;
        private Integer userId;
        private String username;
        private Timestamp createdAt;

        public DTO(Reply reply) {
            this.id = reply.getId();
            this.comment = reply.getComment();
            this.userId = reply.getUser().getId();
            this.username = reply.getUser().getUsername();
            this.createdAt = reply.getCreatedAt();
        }
    }
}
