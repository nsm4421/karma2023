package com.karma.commerce.service;

import com.karma.commerce.domain.Category;
import com.karma.commerce.domain.ProductDto;
import com.karma.commerce.domain.ProductEntity;
import com.karma.commerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<ProductDto> getProducts(Category category, Pageable pageable) {
        if (category == null){
            return productRepository.findAll(pageable).map(ProductDto::from);
        }
        return productRepository.findByCategory(category, pageable).map(ProductDto::from);
    }

    @Transactional(readOnly = true)
    public ProductDto getProduct(Long id){
        return ProductDto.from(productRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundException("Invalid product id is given");
        }));
    }

    public ProductDto updateProduct(Long id, String description){
        ProductEntity entity = productRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundException("Invalid product id is given");
        });
        entity.setDescription(description);
        return ProductDto.from(productRepository.save(entity));
    }
}