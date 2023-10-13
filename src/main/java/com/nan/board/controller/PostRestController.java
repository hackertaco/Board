package com.nan.board.controller;

import com.nan.board.global.PageOption;
import com.nan.board.service.PostService;
import com.nan.board.service.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;
    @PostMapping("/post")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity addPost(@RequestBody PostDto.Request dto){
       return ResponseEntity.ok(postService.savePost(dto));
    }

    @GetMapping("/post")
    public ResponseEntity readPost(@RequestParam Long id) {
        postService.updateView(id);
        return ResponseEntity.ok(postService.getPost(id));
    }
    @PutMapping("/post")
    public ResponseEntity updatePost(@RequestParam Long id, @RequestBody PostDto.Request dto){
        postService.updatePost(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/post")
    public ResponseEntity deletePost(@RequestParam Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/post/all")
    public ResponseEntity getAllPost(@RequestParam(required = false) int pageNum, @RequestParam(required = false) String property){
        PageOption pageOption = new PageOption(pageNum, property);
        return ResponseEntity.ok(postService.getAllPost(pageOption));
    }

    @GetMapping("/post/search")
    public ResponseEntity getPostsByTitle(@RequestParam String title){
        return ResponseEntity.ok(postService.getPostsByTitle(title));
    }
}
