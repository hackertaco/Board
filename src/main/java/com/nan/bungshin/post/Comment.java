package com.nan.bungshin.post;

//import com.nan.bungshin.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String comment;
    @Column(name = "created_date")
    @CreatedDate
    private String createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private String modifiedDate;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user; // 작성자

    public void update(String comment) {
        this.comment = comment;
    }
}
