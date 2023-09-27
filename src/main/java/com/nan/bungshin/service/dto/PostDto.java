package com.nan.bungshin.service.dto;

import com.nan.bungshin.domain.Post;
import com.nan.bungshin.domain.PostHashtag;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class PostDto {
    @Data
    @NoArgsConstructor
    public static class Request {
        private String title;
        private String author;
        private String content;
        private List<String> hashtags;
        public Request( String title, String author, String content, List<String>hashtags){
            this.title = title;
            this.author = author;
            this.content = content;
            this.hashtags = hashtags;
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
//        private final Long userId;
        private final List<CommentDto.Response> comments;
        private List<PostHashtagDto.Response> hashtags;
        public Response(Post post){
            this.id = post.getId();
            this.title = post.getTitle();
            this.author = post.getAuthor();
            this.content = post.getContent();
            this.createdDate = post.getCreatedDate();
            this.modifiedDate = post.getModifiedDate();
            this.view = post.getView();
//            this.userId = post.getUser().getId();
            this.comments = post.getComments().stream().map(CommentDto.Response::new).collect(Collectors.toList());


        }
        public Response getHashtag(Post post, List<PostHashtag> hashtags) {
            Response response = new Response(post);
            System.out.println(hashtags.stream().map(h->h.getHashtag().getName()).collect(Collectors.toList()));
            response.hashtags =  hashtags.stream().map(PostHashtagDto.Response::new).collect(Collectors.toList());
            return response;
        }
    }

}
