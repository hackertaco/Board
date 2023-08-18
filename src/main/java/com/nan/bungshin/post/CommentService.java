package com.nan.bungshin.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Long saveComment(Long id, CommentDto.Request dto) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. " + id));
        dto.setPost(post);
        Comment comment = dto.toEntity();
        commentRepository.save(comment);
        return comment.getId();
    }
    public List<CommentDto.Response> getComment(Long id){
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. " + id));
        List<Comment> comments = post.getComments();
        return comments.stream().map(CommentDto.Response::new).collect(Collectors.toList());
    }
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
