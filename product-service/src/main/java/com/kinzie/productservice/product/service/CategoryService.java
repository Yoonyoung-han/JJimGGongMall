package com.kinzie.productservice.product.service;

import com.kinzie.productservice.product.domain.Product;
import com.kinzie.productservice.product.dto.response.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategories();

    List<CategoryResponseDto> getSubcategories(Long categoryId);

    String getCategoryHierarchyByProduct(Product product);


    CategoryResponseDto getCategoryHierarchyByCategorytId(Long categoryId);
}
