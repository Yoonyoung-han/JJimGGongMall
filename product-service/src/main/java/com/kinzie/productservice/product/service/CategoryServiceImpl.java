package com.kinzie.productservice.product.service;

import com.kinzie.productservice.product.domain.Category;
import com.kinzie.productservice.product.domain.Product;
import com.kinzie.productservice.product.dto.response.CategoryResponseDto;
import com.kinzie.productservice.product.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .filter(category -> category.getParentId() == 0) // 최상위 카테고리 필터링
                .map(CategoryResponseDto::toDto) // 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryResponseDto> getSubcategories(Long categoryId) {
        List<Category> categories = categoryRepository.findAllByParentId(categoryId);
        return categories.stream()
                .map(CategoryResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String getCategoryHierarchyByProduct(Product product) {
        Long categoryId = product.getCategory().getId();
        return categoryRepository.findCategoryHierarchyPathByCategorytId(categoryId);
    }

    @Override
    public CategoryResponseDto getCategoryHierarchyByCategorytId(Long categoryId) {
        List<Category> results = categoryRepository.findCategoryHierarchyByCategorytId(categoryId);

        if (results.isEmpty()) {
            throw new EntityNotFoundException("Category not found for id: " + categoryId);
        }

        // 최상위 카테고리를 찾고 하위 카테고리를 재귀적으로 추가
        Map<Long, CategoryResponseDto> categoryMap = new HashMap<>();
        CategoryResponseDto root = null;

        for (Category category : results) {
            Long id = category.getId();
            Long parentId = category.getParentId();

            CategoryResponseDto dto = CategoryResponseDto.builder()
                    .categoryId(id)
                    .name(category.getName())
                    .parentId(parentId)
                    .depth(category.getDepth())
                    .children(new ArrayList<>())
                    .build();

            if (parentId == null) {
                root = dto;
            } else {
                CategoryResponseDto parentDto = categoryMap.get(parentId);
                if (parentDto != null) {
                    parentDto.getChildren().add(dto);
                }
            }

            categoryMap.put(id, dto);
        }

        return root;
    }
}
