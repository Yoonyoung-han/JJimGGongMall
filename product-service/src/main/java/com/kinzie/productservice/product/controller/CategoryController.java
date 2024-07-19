package com.kinzie.productservice.product.controller;

import com.kinzie.productservice.common.ApiResponse;
import com.kinzie.productservice.product.dto.response.CategoryResponseDto;
import com.kinzie.productservice.product.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<List<CategoryResponseDto>> getAllCategories(){
        List<CategoryResponseDto> result = categoryService.getAllCategories();
        return ApiResponse.ok(result);
    }

    @GetMapping("/{categoryId}/subcategories")
    public ApiResponse<List<CategoryResponseDto>> getSubCategories(@PathVariable Long categoryId){
        List<CategoryResponseDto> result = categoryService.getSubcategories(categoryId);
        return ApiResponse.ok(result);
    }
}
