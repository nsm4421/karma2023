package com.karma.commerce.controller;

import com.karma.commerce.controller.request.UpdateProductRequest;
import com.karma.commerce.domain.ProductDto;
import com.karma.commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable Long productId){
        return productService.getProduct(productId);
    }

    @PutMapping
    public ProductDto getProduct(@RequestBody UpdateProductRequest req){
        return productService.updateProduct(req.getId(), req.getDescription());
    }
}
