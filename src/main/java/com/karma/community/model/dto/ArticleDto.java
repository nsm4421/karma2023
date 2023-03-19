package com.karma.community.model.dto;


import com.karma.community.model.entity.Article;

import java.util.Set;
import java.util.stream.Collectors;

public record ArticleDto(
        UserAccountDto author,
        String title,
        String content,
        Set<ArticleCommentDto> articleComments,
        Set<String> images,
        Set<String> hashtags
) {
    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                UserAccountDto.from(entity.getAuthor()),
                entity.getTitle(),
                entity.getContent(),
                entity.getArticleComments().stream().map(ArticleCommentDto::from).collect(Collectors.toSet()),
                entity.getImages(),
                entity.getHashtags()
        );
    }
}