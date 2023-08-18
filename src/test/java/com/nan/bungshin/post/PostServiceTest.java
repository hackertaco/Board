package com.nan.bungshin.post;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    PostDto.Request dto = new PostDto.Request();
    @BeforeEach
    void beforeEach(){
        dto.setId(1L);
        dto.setAuthor("taco");
        dto.setTitle("test title");
        dto.setContent("test content");
    }

    @AfterEach
    void afterEach(){
        postRepository.deleteAll();
    }
    @Test
    @DisplayName("테스트 글 쓰기")
    void savePost() {
        Long id = postService.savePost(dto);
        assertEquals(id, 1L);
    }

    @Test
    @DisplayName("테스트 글 찾기")
    void getPost() {
        Long id = postService.savePost(dto);
        PostDto.Response post = postService.getPost(id);
        assertThat(post.getId()).isSameAs(1L);
        assertThat(post.getTitle()).isEqualTo("test title");
    }

    @Test
    void updatePost() {
    }

    @Test
    void deletePost() {
    }
}