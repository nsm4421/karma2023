package com.karma.commerce.controller;

import com.karma.commerce.controller.request.UpdateProductRequest;
import com.karma.commerce.controller.response.GetProductResponse;
import com.karma.commerce.domain.Category;
import com.karma.commerce.domain.ProductDto;
import com.karma.commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.el.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Page<GetProductResponse> getProducts(
            @PageableDefault Pageable pageable,
            @RequestParam(name = "category", required = false) Category category
    ){
        return productService.getProducts(category, pageable).map(GetProductResponse::from);
    }

    @GetMapping("/category")
    public List<Map<String,String>> getCategory(){
        List<Map<String,String>> categories = new ArrayList<>();
        for (Category cat : Category.values()) {
            categories.add(Map.of("label", cat.getDescription(), "value", cat.name()));
        }
        return categories;
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
