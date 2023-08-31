package com.nan.bungshin.service;

import com.nan.bungshin.domain.Post;
import com.nan.bungshin.domain.User;
import com.nan.bungshin.persistence.PostRepository;
import com.nan.bungshin.persistence.UserRepository;
import com.nan.bungshin.service.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Transactional
    public Long savePost(PostDto.Request dto){
//        User user= userRepository.findByNickname(nickname);
//        dto.setUser(user);
        log.info("PostsService savePost() 실행", dto);
        Post post = dto.toEntity();
        postRepository.save(post);

        return post.getId();
    }
    @Transactional(readOnly = true)
    public PostDto.Response getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        return new PostDto.Response(post);
    }

    public void updatePost(Long id, PostDto.Request dto){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        post.update(dto.getTitle(), dto.getContent());
    }

    public void deletePost(Long id){
        log.info(String.valueOf(id));
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        postRepository.delete(post);
    }
}
