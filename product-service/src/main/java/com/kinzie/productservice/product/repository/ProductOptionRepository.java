package com.kinzie.productservice.product.repository;

import com.kinzie.productservice.product.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption,Long> {
    List<ProductOption> findByIdIn(List<Long> optionList);
}
