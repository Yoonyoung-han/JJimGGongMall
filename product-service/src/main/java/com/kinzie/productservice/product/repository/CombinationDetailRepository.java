package com.kinzie.productservice.product.repository;

import com.kinzie.productservice.product.domain.CombinationDetail;
import com.kinzie.productservice.product.domain.ProductOptionCombination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CombinationDetailRepository extends JpaRepository<CombinationDetail, Long> {
    List<CombinationDetail> findByProductOptionCombination(ProductOptionCombination productOptionCombination);
}
