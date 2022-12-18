package com.karma.prj.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String createdBy;
    private String modifiedBy;

    private PostDto(Long id, String title, String content, String nickname, LocalDateTime createdAt, LocalDateTime modifiedAt, String createdBy, String modifiedBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    protected PostDto(){}

    public static PostDto of(Long id, String title, String content, String nickname, LocalDateTime createdAt, LocalDateTime modifiedAt, String createdBy, String modifiedBy) {
        return new PostDto(id, title, content, nickname, createdAt, modifiedAt, createdBy, modifiedBy);
    }
}