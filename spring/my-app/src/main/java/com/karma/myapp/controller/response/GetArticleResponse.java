package com.karma.myapp.controller.response;

import com.karma.myapp.domain.dto.ArticleDto;

import java.time.LocalDateTime;

public record GetArticleResponse (
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt
){
    public static GetArticleResponse from(ArticleDto dto){
        return new GetArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.createdAt(),
                dto.user().username(),
                dto.modifiedAt()
        );
    }
}
