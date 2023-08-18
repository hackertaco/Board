package com.nan.bungshin.post;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class PostDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private Long id;
        private String title;
        private String author;
        private String content;
        private String createdDate, modifiedDate;
        private int view;
        public Post toEntity() {
            Post posts = Post.builder()
                    .id(id)
                    .title(title)
                    .author(author)
                    .content(content)
                    .view(0)
                    .build();

            return posts;
        }
    }

    @Getter
    public static class Response {
        private final Long id;
        private final String title;
        private final String author;
        private final String content;
        private final String createdDate, modifiedDate;
        private final int view;
        private final List<CommentDto.Response> comments;
        public Response(Post post){
            this.id = post.getId();
            this.title = post.getTitle();
            this.author = post.getAuthor();
            this.content = post.getAuthor();
            this.createdDate = post.getCreatedDate();
            this.modifiedDate = post.getModifiedDate();
            this.view = post.getView();
            this.comments = post.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());


        }
    }

}
