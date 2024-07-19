package com.kinzie.productservice.product.controller.internal;

import com.kinzie.productservice.product.dto.response.ProductOptionCombinationResponseDto;
import com.kinzie.productservice.product.dto.response.CategoryResponseDto;
import com.kinzie.productservice.product.dto.response.ProductResponseDto;
import com.kinzie.productservice.product.service.CategoryService;
import com.kinzie.productservice.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/internal")
public class InternalController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public InternalController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products/{productId}")
    public ProductResponseDto getProductById(@PathVariable Long productId){
        return productService.getProductById(productId);
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryResponseDto getCategoryById(@PathVariable Long categoryId){
        return categoryService.getCategoryHierarchyByCategorytId(categoryId);
    }

    @GetMapping("/product-combinations/{combinationId}")
    public ProductOptionCombinationResponseDto getProductOptionCombinationById(@PathVariable Long combinationId){
        return productService.getProductOptionCombinationById(combinationId);
    }
}
