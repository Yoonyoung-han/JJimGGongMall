package com.kinzie.productservice.product.repository;

import com.kinzie.productservice.product.domain.ProductOptionCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionCombinationRepository extends JpaRepository<ProductOptionCombination,Long> {
    List<ProductOptionCombination> findByProductId(Long productId);
}
