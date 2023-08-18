package com.nan.bungshin.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentRestController {
    private final CommentService commentService;
    @PostMapping("/post/{id}/comment")
    public ResponseEntity addComment(@PathVariable Long id, @RequestBody CommentDto.Request dto) {
        return ResponseEntity.ok(commentService.saveComment(id, dto));
    }
    @GetMapping("post/{id}/comment")
    public List<CommentDto.Response> getComment(@PathVariable Long id){
        return commentService.getComment(id);
    }
    @PutMapping("post/{postId}/comment/{id}")
    public ResponseEntity updateComment(@PathVariable Long postId, @PathVariable Long id, @RequestBody CommentDto.Request dto) {
        commentService.updateComment(postId, id, dto);
        return ResponseEntity.ok(id);
    }
    @DeleteMapping("post/{postId}/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable Long postId, @PathVariable Long id) {
        commentService.deleteComment(postId, id);
        return ResponseEntity.ok(id);
    }
}
