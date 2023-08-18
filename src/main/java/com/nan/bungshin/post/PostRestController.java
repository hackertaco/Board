package com.nan.bungshin.post;

import com.nan.bungshin.global.ResponseForm;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostRestController {
    private final PostService postService;
    @PostMapping("/post")
    public ResponseEntity addPost(@RequestBody PostDto.Request dto){
       return ResponseEntity.ok( postService.savePost(dto));
    }
    @GetMapping("/post/{id}")
    public ResponseEntity readPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }
    @PutMapping("/post/{id}")
    public ResponseEntity updatePost(@PathVariable Long id, @RequestBody PostDto.Request dto){
        postService.updatePost(id, dto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(id);
    }
}
