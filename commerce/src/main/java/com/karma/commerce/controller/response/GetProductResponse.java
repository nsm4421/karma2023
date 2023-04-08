package com.karma.commerce.controller.response;

import com.karma.commerce.domain.ProductDto;
import lombok.Data;

@Data
public class GetProductResponse {
    private Long id;
    private String name;
    private String imgUrl;
    private Long categoryId;
    private String description;
    private Long price;

    private GetProductResponse(Long id, String name, String imgUrl, Long categoryId, String description, Long price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
    }

    protected GetProductResponse(){}

    private static GetProductResponse of(Long id, String name, String imgUrl, Long categoryId, String description, Long price){
        return new GetProductResponse(id, name, imgUrl, categoryId, description, price);
    }

    public static GetProductResponse from(ProductDto dto){
        return GetProductResponse.of(
                dto.id(),
                dto.name(),
                dto.imgUrl(),
                dto.categoryId(),
                dto.description(),
                dto.price()
        );
    }
}
