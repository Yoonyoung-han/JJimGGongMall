package com.kinzie.orderservice.common.dto.response;

import com.kinzie.orderservice.order.domain.CombinationDetail;
import com.kinzie.orderservice.order.domain.ProductOptionCombination;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductOptionCombinationResponseDto {

    private Long productOptionCombinationId;
    private Long productId;
    private String combinationValue;
    private String description;
    private String thumbnailUrl;
    private BigDecimal price;
    private Long stock;
    List<CombinationDetail> combinationDetails;

    @Builder
    public ProductOptionCombinationResponseDto(Long productOptionCombinationId, Long productId,
                                               String combinationValue, String description, String thumbnailUrl,
                                               BigDecimal price, Long stock,
                                               List<CombinationDetail> combinationDetails) {
        this.productOptionCombinationId = productOptionCombinationId;
        this.productId = productId;
        this.combinationValue = combinationValue;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.price = price;
        this.stock = stock;
        this.combinationDetails = combinationDetails;
    }


    public static ProductOptionCombinationResponseDto toDto(ProductOptionCombination entity){
        return ProductOptionCombinationResponseDto.builder()
                .productId(entity.getProduct().getId())
                .price(entity.getPrice())
                .combinationValue(entity.getCombinationValue())
                .description(entity.getProduct().getProductDescription())
                .stock(entity.getStock())
                .productOptionCombinationId(entity.getId())
                .thumbnailUrl(entity.getProduct().getThumbnailUrl())
                .combinationDetails(entity.getCombinationDetails())
                .build();
    }
}
