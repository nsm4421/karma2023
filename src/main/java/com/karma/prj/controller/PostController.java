package com.karma.prj.controller;

import com.karma.prj.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;
    @PostMapping
    public void create(String title, String content){
        // TODO : JWT 받아서 username 추출
        String username="test";
        postService.create(title, content, username);
    }
}
