package com.nan.bungshin.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "posts")
public class Post extends BaseTimeEntity{
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



    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Comment> comments = new ArrayList<>();
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
