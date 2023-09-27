package com.nan.bungshin.service.dto;

import com.nan.bungshin.domain.PostHashtag;

import lombok.Getter;


public class PostHashtagDto {
    @Getter
    public static class Response {
        private final Long id;
        private final String name;

        public Response(PostHashtag postHashtag) {
            this.name = postHashtag.getHashtag().getName();
            this.id = postHashtag.getId();
        }

    }
}