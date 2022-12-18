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
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private PostDto(Long id, String title, String content, String username, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    protected PostDto(){}

    public static PostDto of(Long id, String title, String content, String username, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new PostDto(id, title, content, username, createdAt, modifiedAt);
    }
}