package com.nan.bungshin.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import java.util.List;
import java.util.Optional;

@Getter
public class FindByHashTagDTO {
    private long id;
    private int view;
    private String author;
    private String title;
    private String content;
    @JsonIgnore

    private String name;

    private Optional<List<String>> hashtags;


    public FindByHashTagDTO(IfindByHashtagDTO h) {
        this.id = h.getId();
        this.view = h.getView();
        this.author = h.getAuthor();
        this.title = h.getTitle();
        this.content= h.getContent();
        this.name = h.getName();
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = Optional.ofNullable(hashtags);
    }

}
