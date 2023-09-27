package com.nan.bungshin.service;

import com.nan.bungshin.domain.Post;
import com.nan.bungshin.domain.PostHashtag;
import com.nan.bungshin.global.PageOption;
import com.nan.bungshin.persistence.PostHashtagRepository;
import com.nan.bungshin.persistence.PostRepository;
import com.nan.bungshin.persistence.UserRepository;
import com.nan.bungshin.service.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final PostRepository postRepository;
    private final PostHashtagService postHashtagService;
    @Transactional
    public Long savePost(PostDto.Request dto){
//        User user= userRepository.findByNickname(nickname);
//        dto.setUser(user);
        log.info("PostsService savePost() 실행", dto);
        Post post = new Post(dto);
        postRepository.save(post);
        postHashtagService.saveHashtag(post, dto.getHashtags());

        return post.getId();
    }
    @Transactional(readOnly = true)
    public PostDto.Response getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        return new PostDto.Response(post);
    }

    public List<PostDto.Response> getAllPost(PageOption pageOption){
        List<PostDto.Response> list = new ArrayList<>();
        Pageable page = PageRequest.of(pageOption.getPageNum(), pageOption.getSize(), pageOption.getSort());

        Page<Post> posts = postRepository.findAll(page);
        log.info(posts.toString());
        for(Post post: posts){
            List<PostHashtag> hashtags = postHashtagService.findHashtagListByPost(post);
            list.add(new PostDto.Response(post).getHashtag(post, hashtags));
        }

        return list;
    }
    @Transactional
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
    @Transactional
    public int updateView(Long id){
        return postRepository.updateView(id);
    }

    public List<PostDto.Response> getPostsByTitle(String title){
        List<Post> posts = postRepository.findByTitleContains(title);
        List<PostDto.Response> list = new ArrayList<>();
        for(Post post: posts){
            List<PostHashtag> hashtags = postHashtagService.findHashtagListByPost(post);
            list.add(new PostDto.Response(post).getHashtag(post, hashtags));
        }
        return list;
    }
}
