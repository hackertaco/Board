package com.nan.bungshin.domain;

import com.nan.bungshin.service.dto.PostDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@NoArgsConstructor
@Getter
@Table(name = "posts")
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 500, nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<PostHashtag> postHashtags;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
    @Builder
    public Post(PostDto.Request dto)
    {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.content = dto.getContent();
        this.view = 0;

    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", view=" + view +
                ", comments=" + comments +
                '}';
    }
}
