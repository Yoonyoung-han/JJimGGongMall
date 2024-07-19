package com.kinzie.orderservice.client;

import com.kinzie.orderservice.common.dto.response.CategoryResponseDto;
import com.kinzie.orderservice.common.dto.response.ProductOptionCombinationResponseDto;
import com.kinzie.orderservice.common.dto.response.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductServiceClient {
    @GetMapping("/api/internal/products/{productId}")
    ProductResponseDto getProductById(@PathVariable Long productId);

    @GetMapping("/api/internal/categories/{categoryId}")
    CategoryResponseDto getCategoryById(@PathVariable Long categoryId);

    @GetMapping("/product-combinations/{combinationId}")
    ProductOptionCombinationResponseDto getProductOptionCombinationById(@PathVariable Long combinationId);
}
