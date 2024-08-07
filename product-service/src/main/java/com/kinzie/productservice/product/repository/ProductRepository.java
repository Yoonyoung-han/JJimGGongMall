package com.kinzie.productservice.product.repository;

import com.kinzie.productservice.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product,Long> {
    List<Product> findAllByCategoryIdIn(List<Long> categoryIds);

    List<Product> findAllByIdIn(List<Long> connectIds);
}

