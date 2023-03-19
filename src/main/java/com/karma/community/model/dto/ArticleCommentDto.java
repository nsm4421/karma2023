package com.karma.community.model.dto;

import com.karma.community.model.entity.ArticleComment;

public record ArticleCommentDto(
        Long articleId,
        Long userId,
        Long parentCommentId,
        String content
) {
    public static ArticleCommentDto from(ArticleComment entity) {
        return new ArticleCommentDto(
                entity.getArticle().getId(),
                entity.getUserAccount().getId(),
                entity.getParentCommentId(),
                entity.getContent()
        );
    }
}
