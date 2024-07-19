package com.kinzie.orderservice.order.domain;

import com.kinzie.orderservice.common.BaseEntity;
import com.kinzie.orderservice.common.dto.response.ProductResponseDto;
import com.kinzie.orderservice.common.exception.CustomLogicException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

import static com.kinzie.orderservice.common.exception.ErrorCode.OUT_OF_STOCK;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
@Slf4j
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "prod_name")
    private String productName;

    @Column(name = "prod_desc")
    private String productDescription;
    private Long stock;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
    private String thumbnailUrl;

    @Builder
    public Product(Category category, String productName, String productDescription, Long stock, BigDecimal price, String thumbnailUrl) {
        this.category = category;
        this.productName = productName;
        this.productDescription = productDescription;
        this.stock = stock;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
    }

    public void decrease(Long quantity) {
        verifyStockAvailable(quantity);
        stock -= quantity;
    }

    public void verifyStockAvailable(Long quantity) {
        if (stock - quantity < 0) {
            throw CustomLogicException.createBadRequestError(OUT_OF_STOCK);
        }
    }

    public void increase(Long quantity) {
        stock += quantity;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public void setId(Long productId){
        this.id = productId;
    }

    public static Product of(ProductResponseDto responseDto){
        Product product = Product.builder()
                .productName(responseDto.getProductName())
                .price(responseDto.getPrice())
                .stock(responseDto.getStock())
                .productDescription(responseDto.getDescription())
                .thumbnailUrl(responseDto.getThumbnailUrl())
                .build();

        product.setId(responseDto.getProductId());
        return product;
    }
}
