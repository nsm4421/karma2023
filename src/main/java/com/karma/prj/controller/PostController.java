package com.karma.prj.controller;

import com.karma.prj.controller.request.*;
import com.karma.prj.model.dto.CommentDto;
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
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    @GetMapping("/post/{postId}")
    public CustomResponse<PostDto> getPost(@PathVariable Long postId){
        return CustomResponse.success(postService.getPost(postId));
    }
    @GetMapping("/post")
    public CustomResponse<Page<PostDto>> getPosts(@PageableDefault Pageable pageable){
        return CustomResponse.success(postService.getPosts(pageable));
    }
    @PostMapping("/post")
    public CustomResponse<Long> createPost(@RequestBody CreatePostRequest req, Authentication authentication){
        return CustomResponse.success(postService.createPost(req.getTitle(), req.getContent(), authentication.getName()));
    }

    @PutMapping("/post/{postId}")
    public CustomResponse<Long> modifyPost(@PathVariable Long postId, @RequestBody ModifyPostRequest req, Authentication authentication){
        return CustomResponse.success(postService.modifyPost(postId, req.getTitle(), req.getContent(), authentication.getName()));
    }
    @DeleteMapping("/post/{postId}")
    public CustomResponse<Long> deletePost(@PathVariable Long postId, Authentication authentication){
        return CustomResponse.success(postService.deletePost(postId, authentication.getName()));
    }

    @GetMapping("/comment/{postId}")
    public CustomResponse<Page<CommentDto>> getComment(@PathVariable Long postId, @PageableDefault Pageable pageable){
        return CustomResponse.success(postService.getComments(postId, pageable));
    }

    @PostMapping("/comment")
    public CustomResponse<CommentDto> createPost(@RequestBody CreateCommentRequest req, Authentication authentication){
        return CustomResponse.success(postService.createComment(req.getPostId(), req.getContent(), authentication.getName()));
    }

    @PutMapping("/comment")
    public CustomResponse<CommentDto> modifyPost(@RequestBody ModifyCommentRequest req, Authentication authentication){
        return CustomResponse.success(postService.modifyComment(req.getPostId(), req.getCommentId(), req.getContent(), authentication.getName()));
    }

    @DeleteMapping("/comment")
    public CustomResponse<Void> deletePost(@RequestBody DeleteCommentRequest req, Authentication authentication){
        postService.deleteComment(req.getPostId(), req.getCommentId(), authentication.getName());
        return CustomResponse.success();
    }
}
