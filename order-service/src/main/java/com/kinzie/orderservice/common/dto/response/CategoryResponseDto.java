package com.kinzie.orderservice.common.dto.response;

import com.kinzie.orderservice.order.domain.Category;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryResponseDto {
    private Long categoryId;
    private String name;
    private Long parentId;
    private String code;
    private int depth;
    private List<CategoryResponseDto> children;

    @Builder
    public CategoryResponseDto(Long categoryId, String name, Long parentId, int depth,
                               String code, List<CategoryResponseDto> children) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
        this.depth = depth;
        this.code = code;
        this.children = children;
    }

    public static CategoryResponseDto toDto(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getId())
                .parentId(category.getParentId())
                .depth(category.getDepth())
                .name(category.getName())
                .code(category.getCode())
                .children(category.getChildren().stream().map(CategoryResponseDto::toDto).collect(Collectors.toList()))
                .build();
    }
}