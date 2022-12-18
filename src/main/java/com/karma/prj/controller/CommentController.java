package com.karma.prj.controller;

import com.karma.prj.controller.request.CreateCommentRequest;
import com.karma.prj.controller.request.DeleteCommentRequest;
import com.karma.prj.controller.request.ModifyCommentRequest;
import com.karma.prj.model.dto.CommentDto;
import com.karma.prj.model.util.CustomResponse;
import com.karma.prj.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public CustomResponse<Page<CommentDto>> getComment(@PathVariable Long postId, @PageableDefault Pageable pageable){
        return CustomResponse.success(commentService.getComments(postId, pageable));
    }

    @PostMapping
    public CustomResponse<CommentDto> create(@RequestBody CreateCommentRequest req, Authentication authentication){
        return CustomResponse.success(commentService.create(req.getPostId(), req.getContent(), authentication.getName()));
    }

    @PutMapping
    public CustomResponse<CommentDto> modify(@RequestBody ModifyCommentRequest req, Authentication authentication){
        return CustomResponse.success(commentService.modify(req.getPostId(), req.getCommentId(), req.getContent(), authentication.getName()));
    }

    @DeleteMapping
    public CustomResponse<Void> delete(@RequestBody DeleteCommentRequest req, Authentication authentication){
        commentService.delete(req.getPostId(), req.getCommentId(), authentication.getName());
        return CustomResponse.success();
    }
}
