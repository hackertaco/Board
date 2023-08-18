package com.nan.bungshin.post;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{
        private Long id;
        private String comment;
        private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        private Post post;
        public Comment toEntity(){
            Comment comments = Comment.builder()
                    .id(id)
                    .comment(comment)
                    .createdDate(createdDate)
                    .modifiedDate(modifiedDate)
                    .post(post)
                    .build();
            return comments;
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
        public Response(Comment comment) {
            this.id = comment.getId();
            this.comment = comment.getComment();
            this.createdDate = comment.getCreatedDate();
            this.modifiedDate = comment.getModifiedDate();
            this.postId = comment.getPost().getId();
        }
    }
}
