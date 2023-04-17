package com.karma.myapp.domain.dto;

import com.karma.myapp.domain.entity.ArticleCommentEntity;

public record ArticleCommentDto(
        Long id,
        ArticleDto article,
        UserAccountDto user,
        String content
) {
    public static ArticleCommentDto of(
        ArticleDto article,
        String content
    ){
        return new ArticleCommentDto(
                null,
                article,
                article.user(),
                content
        );
    }
    public static ArticleCommentDto from(ArticleCommentEntity entity){
        return new ArticleCommentDto(
                entity.getId(),
                ArticleDto.from(entity.getArticle()),
                UserAccountDto.from(entity.getUser()),
                entity.getContent()
        );
    }
}
