package com.nan.board.controller;


import com.nan.board.global.PageOption;
import com.nan.board.service.PostHashtagService;
import com.nan.board.service.dto.FindByHashTagDTO;
import com.nan.board.service.dto.PostHashtagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HashtagController {
    private final PostHashtagService postHashtagService;
    @GetMapping("/hashtags/all")
    public ResponseEntity<List<PostHashtagDto.Response>> getAllHashtags(){
        return ResponseEntity.ok(postHashtagService.getAllHashtags());
    }
    @GetMapping("/search-hashtag")
    public ResponseEntity<List<FindByHashTagDTO>> searchPostByHashtag(@RequestParam String searchParam){
        PageOption pageOption = new PageOption(0);
        return ResponseEntity.ok(postHashtagService.getPostByHashtag(searchParam));
    }
}
