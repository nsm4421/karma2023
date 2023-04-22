package com.karma.myapp.domain.dto;

import com.karma.myapp.domain.entity.HashtagEntity;

import java.time.LocalDateTime;

public record HashtagDto (
        Long id,
        ArticleDto article,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        LocalDateTime removedAt
){
    public static HashtagDto from(HashtagEntity entity){
        return new HashtagDto(
                entity.getId(),
                ArticleDto.from(entity.getArticle()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy(),
                entity.getRemovedAt()
        );
    }
}
