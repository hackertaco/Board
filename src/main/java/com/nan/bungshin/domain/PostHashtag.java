package com.nan.bungshin.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Entity
@AllArgsConstructor
public class PostHashtag extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
//    @JoinTable(name="post", joinColumns = @JoinColumn(name = "post_hashtag_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @Builder
    public PostHashtag(Post post, Hashtag hashtag) {
        this.post = post;
        this.hashtag = hashtag;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PostHashtag){
            return this.post.getId().equals(((PostHashtag) obj).getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "PostHashtag{" +
                "id=" + id +
                ", post=" + post +
                ", hashtag=" + hashtag +
                '}';
    }
}
