package com.kinzie.productservice.product.controller;

import com.kinzie.productservice.common.ApiResponse;
import com.kinzie.productservice.product.dto.ProductDetailDto;
import com.kinzie.productservice.product.dto.response.ProductResponseDto;
import com.kinzie.productservice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponseDto>> getAllProducts(){
        List<ProductResponseDto> result = productService.getAllProducts();
        return ApiResponse.ok(result);
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductDetailDto> getProductDetail(@PathVariable Long productId){
        ProductDetailDto productDetail = productService.getProductDetail(productId);
        return ApiResponse.ok(productDetail);
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductResponseDto>> getProductsByCategory(@PathVariable Long categoryId){
        List<ProductResponseDto> products = productService.getProductsByCategory(categoryId);
        return ApiResponse.ok(products);
    }
}
