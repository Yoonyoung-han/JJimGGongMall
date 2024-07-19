package com.kinzie.orderservice.order.domain;

import com.kinzie.orderservice.common.BaseEntity;
import com.kinzie.orderservice.common.dto.response.CategoryResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categories")
@Slf4j
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    private String code;
    private String name;
    private int depth;

    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Category parentCategory;

    @Builder
    public Category(Long parentId, String code, String name,
                    int depth, List<Category> children) {
        this.parentId = parentId;
        this.code = code;
        this.name = name;
        this.depth = depth;
    }

    public static Category of(CategoryResponseDto categoryDto){
        return Category.builder()
                .parentId(categoryDto.getParentId())
                .code(categoryDto.getCode())
                .name(categoryDto.getName())
                .depth(categoryDto.getDepth())
                .children(categoryDto.getChildren().stream().map(Category::of).collect(Collectors.toList()))
                .build();
    }

}
