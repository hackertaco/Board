package com.nan.bungshin.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    PostRepository postRepository;
    @InjectMocks
    PostService postService;



//    @AfterEach
//    void afterEach(){
//        postRepository.deleteAll();
//    }
    @Test
    @DisplayName("테스트 글 쓰기")
    void savePost() {
        PostDto.Request post = post();
        Long id = postService.savePost(post);
        assertEquals(id, 1L);
    }

    @Test
    @DisplayName("테스트 글 찾기")
    void getPost() {
        PostDto.Request post = post();
        Long fakeId = 1L;
        given(postRepository.findById(1L))
                .willReturn(Optional.of(new Post(1L,"test title", "taco", "test content", 0, new ArrayList())));
        //when

        PostDto.Response postRes = postService.getPost(fakeId);
        //then
//        assertThat(postRes.getAuthor()).isSameAs("taco");
        assertThat(postRes)
                .hasFieldOrPropertyWithValue("title", post.getTitle())
                .hasFieldOrPropertyWithValue("content", post.getContent());

        BDDMockito.then(postRepository).should().findById(fakeId);
    }

    @Test
    void updatePost() {
    }

    @Test
    void deletePost() {
    }

    private PostDto.Request post() {
        PostDto.Request dto = new PostDto.Request();
        dto.setId(1L);
        dto.setAuthor("taco");
        dto.setTitle("test title");
        dto.setContent("test content");

        return dto;
    }

}