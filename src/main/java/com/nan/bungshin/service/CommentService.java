package com.nan.bungshin.service;

import com.nan.bungshin.domain.Comment;
import com.nan.bungshin.domain.Post;
import com.nan.bungshin.global.PageOption;
import com.nan.bungshin.persistence.CommentRepository;
import com.nan.bungshin.persistence.PostRepository;
import com.nan.bungshin.service.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
//    private final UserRepository userRepository;

    public Long saveComment(Long id, CommentDto.Request dto) {
//        User user = userRepository.findByNickname(nickname);
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + id));
//        dto.setPost(post);
//        dto.setUser(user);
        Comment comment = new Comment(dto, post);
        commentRepository.save(comment);
        return comment.getId();
    }
    public List<CommentDto.Response> getComment(Long id, PageOption pageOption){
//        Post post = postRepository.findById(id).orElseThrow(() ->
//                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id));
        List<CommentDto.Response> list = new ArrayList<>();
        Pageable page = PageRequest.of(pageOption.getPageNum(), pageOption.getSize(), pageOption.getSort());
        Page<Comment> comments = commentRepository.findAllByPostId(id, page);
        log.info(comments.toString());
        for(Comment comment: comments){
            list.add(new CommentDto.Response(comment));
        }
        return list;
    }
    @Transactional
    public void updateComment(Long postId, Long id, CommentDto.Request dto){
        Comment comment = commentRepository.findByPostIdAndId(postId, id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));
        comment.update(dto.getComment());
    }
    public void deleteComment(Long postId, Long id){
        Comment comment = commentRepository.findByPostIdAndId(postId, id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. " + id));
        commentRepository.delete(comment);
    }
}
