package com.nan.board.service.dto;

import com.nan.board.domain.Comment;
import com.nan.board.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentDto {
    @Data
    @NoArgsConstructor
    public static class Request{
        private String comment;
        private User user;
        public Request(String comment){
            this.comment = comment;
        }
    }
    @RequiredArgsConstructor
    @Getter
    public static class Response {
        private Long id;
        private String comment;
        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private Long postId;
        private Long userId;
        public Response(Comment comment) {
            this.id = comment.getId();
            this.comment = comment.getComment();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
            this.userId = comment.getUser().getId();
            this.postId = comment.getPost().getId();
        }
    }
}
