package com.karma.prj.model.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private Long postId;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private CommentDto(Long id, Long postId, String content, String nickname, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.postId = postId;
        this.content = content;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    protected CommentDto(){}

    public static CommentDto of(Long id, Long postId, String content, String nickname, LocalDateTime createdAt, LocalDateTime modifiedAt){
        return new CommentDto(id,postId,content,nickname,createdAt,modifiedAt);
    }



}
