package com.karma.commerce.domain;

import java.time.LocalDateTime;

public record ProductDto(
        Long id,
        String name,
        String imgUrl,
        Category category,
        String description,
        Long price,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {
    public static ProductDto of(
            String name,
            String imgUrl,
            Category category,
            String description,
            Long price
    ){
        return new ProductDto(
                null,
                name,
                imgUrl,
                category,
                description,
                price,
                null,
                null,
                null,
                null
        );
    }
    public static ProductDto from(ProductEntity entity){
        return new ProductDto(
                entity.getId(),
                entity.getName(),
                entity.getImgUrl(),
                entity.getCategory(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}
