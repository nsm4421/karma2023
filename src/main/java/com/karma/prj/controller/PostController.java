package com.karma.prj.controller;

import com.karma.prj.controller.request.*;
import com.karma.prj.controller.response.GetLikeResponse;
import com.karma.prj.controller.response.GetPostResponse;
import com.karma.prj.model.dto.CommentDto;
import com.karma.prj.model.dto.PostDto;
import com.karma.prj.model.entity.UserEntity;
import com.karma.prj.model.util.CustomResponse;
import com.karma.prj.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    // 포스팅 단건조회
    @GetMapping("/post/detail")
    public CustomResponse<GetPostResponse> getPost(@RequestParam("pid") Long postId){
        return CustomResponse.success(GetPostResponse.from(postService.getPost(postId)));
    }

    // 포스팅 페이지 조회
    @GetMapping("/post")
    public CustomResponse<Page<GetPostResponse>> getPosts(
            @PageableDefault(size = 20, sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable
    ){
        return CustomResponse.success(postService.getPosts(pageable).map(GetPostResponse::from));
    }

    // 특정 해쉬태크고로 조회
    @GetMapping("/post/hashtag")
    public CustomResponse<Page<GetPostResponse>> getPostsByHashtag(
            @RequestParam("hashtag") String hashtag,
            @PageableDefault(size = 20, sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable
    ){
        return CustomResponse.success(postService.getPostsByHashtag(pageable, hashtag).map(GetPostResponse::from));
    }

    // 특정 유저가 쓴 조회
    @GetMapping("/post/username")
    public CustomResponse<Page<PostDto>> getPostsByUser(@PageableDefault Pageable pageable, @RequestParam("nickname") String username){
        return CustomResponse.success(postService.getPostsByUser(pageable, username));
    }

    // 포스팅 작성
    @PostMapping("/post")
    public CustomResponse<Long> createPost(@RequestBody CreatePostRequest req, Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return CustomResponse.success(postService.createPost(req.getTitle(), req.getContent(), user, req.getHashtags()));
    }

    // 포스팅 수정
    @PutMapping("/post/{postId}")
    public CustomResponse<Void> modifyPost(@PathVariable Long postId, @RequestBody ModifyPostRequest req, Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        postService.modifyPost(postId, req.getTitle(), req.getContent(), user, req.getHashtags());
        return CustomResponse.success();
    }

    // 포스팅 삭제
    @DeleteMapping("/post/{postId}")
    public CustomResponse<Long> deletePost(@PathVariable Long postId, Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return CustomResponse.success(postService.deletePost(postId, user.getId()));
    }

    // 댓글 조회
    @GetMapping("/comment")
    public CustomResponse<Page<CommentDto>> getComment(
            @RequestParam("pid") Long postId,
            @PageableDefault(size = 10, sort = "createdAt",direction = Sort.Direction.DESC) Pageable pageable
    ){
        return CustomResponse.success(postService.getComments(postId, pageable));
    }

    // 댓글 작성
    @PostMapping("/comment")
    public CustomResponse<Void> createComment(@RequestBody CreateCommentRequest req, Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        postService.createComment(req.getPostId(), req.getContent(), user);
        return CustomResponse.success();
    }

    // 댓글 수정
    @PutMapping("/comment")
    public CustomResponse<CommentDto> modifyPost(@RequestBody ModifyCommentRequest req, Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return CustomResponse.success(postService.modifyComment(req.getPostId(), req.getCommentId(), req.getContent(), user));
    }

    // 댓글 삭제
    @DeleteMapping("/comment")
    public CustomResponse<Void> deletePost(@RequestBody DeleteCommentRequest req, Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        postService.deleteComment(req.getPostId(), req.getCommentId(), user);
        return CustomResponse.success();
    }

    // 좋아요 & 싫어요 개수 가져오기
    @GetMapping("/like")
    public CustomResponse<GetLikeResponse> getLikeCount(@RequestParam("pid") Long postId){
        return CustomResponse.success(GetLikeResponse.from(postId, postService.getLikeCount(postId)));
    }

    // 좋아요 & 싫어요 요청
    @PostMapping("/like")
    public CustomResponse<Void> likePost(@RequestBody LikePostRequest req, Authentication authentication){
        UserEntity user = (UserEntity) authentication.getPrincipal();
        postService.likePost(req.getPostId(), req.getLikeType(), user);
        return CustomResponse.success();
    }
}
