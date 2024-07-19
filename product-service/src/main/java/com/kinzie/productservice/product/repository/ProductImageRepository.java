package com.kinzie.productservice.product.repository;

import com.kinzie.productservice.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    // 상세 이미지들을 반환하는 메서드
    List<ProductImage> findByProductIdAndIsDetailImageTrue(Long productId);

    List<ProductImage> findByProductIdAndIsPreviewImageTrue(Long productId);
}
