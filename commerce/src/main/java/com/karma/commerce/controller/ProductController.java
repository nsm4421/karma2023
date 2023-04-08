package com.karma.commerce.controller;

import com.karma.commerce.controller.request.UpdateProductRequest;
import com.karma.commerce.controller.response.GetProductResponse;
import com.karma.commerce.domain.ProductDto;
import com.karma.commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<GetProductResponse> getProducts(
            @PageableDefault Pageable pageable
    ){
        return productService.getProducts(pageable).map(GetProductResponse::from);
    }

    @GetMapping("/{productId}")
    public GetProductResponse getProduct(@PathVariable Long productId){
        return GetProductResponse.from(productService.getProduct(productId));
    }

    @PutMapping
    public GetProductResponse getProduct(@RequestBody UpdateProductRequest req){
        return GetProductResponse.from(productService.updateProduct(req.getId(), req.getDescription()));
    }
}
