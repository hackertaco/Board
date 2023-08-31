package com.nan.bungshin.post;

//import lombok.extern.log4j.Log4j2;
import com.nan.bungshin.domain.Post;
import com.nan.bungshin.persistence.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
//@Log4j2
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void reset() {
        postRepository.deleteAll();
    }

    @AfterEach
    public void clear() {
        postRepository.deleteAll();
    }
    @Test
    public void 게시글_생성_가져오기() {
        String title = "제목 입니다.";
        String content = "내용 입니다";

        postRepository.save(Post.builder().title(title).content(content).author("coco").view(0).build());

        List<Post> postsList = postRepository.findAll();

        Post post = postsList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);

//        log.info(post);
    }
}