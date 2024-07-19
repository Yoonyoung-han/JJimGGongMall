package com.kinzie.productservice.product.service;

import com.kinzie.productservice.product.dto.ProductDetailDto;
import com.kinzie.productservice.product.dto.response.ProductOptionCombinationResponseDto;
import com.kinzie.productservice.product.dto.response.ProductResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();

    @Transactional
    ProductDetailDto getProductDetail(Long productId);

    List<ProductResponseDto> getProductsByCategory(Long categoryId);

    ProductResponseDto getProductById(Long productId);

    ProductOptionCombinationResponseDto getProductOptionCombinationById(Long combinationId);
}
