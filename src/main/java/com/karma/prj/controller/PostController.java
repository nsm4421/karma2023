package com.karma.prj.controller;

import com.karma.prj.controller.request.CreatePostRequest;
import com.karma.prj.controller.request.ModifyPostRequest;
import com.karma.prj.model.dto.PostDto;
import com.karma.prj.model.util.CustomResponse;
import com.karma.prj.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/{postId}")
    public CustomResponse<PostDto> getPost(@PathVariable Long postId){
        return CustomResponse.success(postService.getPost(postId));
    }
    @GetMapping
    public CustomResponse<Page<PostDto>> getPosts(@PageableDefault Pageable pageable){
        return CustomResponse.success(postService.getPosts(pageable));
    }
    @PostMapping
    public CustomResponse<Long> create(@RequestBody CreatePostRequest req, Authentication authentication){
        return CustomResponse.success(postService.create(req.getTitle(), req.getContent(), authentication.getName()));
    }

    @PutMapping("/{postId}")
    public CustomResponse<Long> modify(@PathVariable Long postId, @RequestBody ModifyPostRequest req, Authentication authentication){
        return CustomResponse.success(postService.modify(postId, req.getTitle(), req.getContent(), authentication.getName()));
    }
    @DeleteMapping("/{postId}")
    public CustomResponse<Long> delete(@PathVariable Long postId,Authentication authentication){
        return CustomResponse.success(postService.delete(postId, authentication.getName()));
    }
}
