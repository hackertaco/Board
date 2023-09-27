package com.nan.bungshin.controller;

//import com.nan.bungshin.global.util.LoginUser;
import com.nan.bungshin.global.PageOption;
import com.nan.bungshin.service.CommentService;
import com.nan.bungshin.service.dto.CommentDto;
//import com.nan.bungshin.service.dto.UserSessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentRestController {
    private final CommentService commentService;
    @PostMapping("/comment")
    public ResponseEntity addComment(@RequestParam Long id, @RequestBody CommentDto.Request dto) {
        return ResponseEntity.ok(commentService.saveComment(id, dto));
    }
    @GetMapping("/comment")
    public ResponseEntity getComment(@RequestParam Long id, @RequestParam int pageNum){
        PageOption pageOption = new PageOption(pageNum);
        return ResponseEntity.ok(commentService.getComment(id, pageOption));
    }
    @PutMapping("/comment")
    public ResponseEntity updateComment(@RequestParam Long postId, @RequestParam Long id, @RequestBody CommentDto.Request dto) {
        commentService.updateComment(postId, id, dto);
        return ResponseEntity.ok(id);
    }
    @DeleteMapping("/comment")
    public ResponseEntity deleteComment(@RequestParam Long postId, @RequestParam Long id) {
        commentService.deleteComment(postId, id);
        return ResponseEntity.ok(id);
    }

}
