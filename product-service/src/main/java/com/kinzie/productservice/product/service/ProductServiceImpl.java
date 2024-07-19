package com.kinzie.productservice.product.service;

import com.kinzie.productservice.product.domain.Product;
import com.kinzie.productservice.product.domain.ProductImage;
import com.kinzie.productservice.product.domain.ProductOption;
import com.kinzie.productservice.product.domain.ProductOptionCombination;
import com.kinzie.productservice.product.dto.response.ProductOptionCombinationResponseDto;
import com.kinzie.productservice.product.dto.OptionDto;
import com.kinzie.productservice.product.dto.ProductDetailDto;
import com.kinzie.productservice.product.dto.response.ProductResponseDto;
import com.kinzie.productservice.product.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final OptionCombinationRepository optionCombinationRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final CombinationDetailRepository combinationDetailRepository;
    private final ProductOptionRepository productOptionRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductImageRepository productImageRepository,
                              OptionCombinationRepository optionCombinationRepository, CategoryService categoryService,
                              CategoryRepository categoryRepository, CombinationDetailRepository combinationDetailRepository,
                              ProductOptionRepository productOptionRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.optionCombinationRepository = optionCombinationRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
        this.combinationDetailRepository = combinationDetailRepository;
        this.productOptionRepository = productOptionRepository;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ProductDetailDto getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : "+ productId));

        ProductDetailDto productDetail = ProductDetailDto.builder()
                .productId(product.getId())
                .price(product.getPrice())
                .name(product.getProductName())
                .thumbnailUrl(product.getThumbnailUrl())
                .description(product.getProductDescription())
                .stock(product.getStock())
                .build();

        // images info
        List<ProductImage> detailImages = productImageRepository.findByProductIdAndIsDetailImageTrue(productId);
        List<ProductImage> previewImages = productImageRepository.findByProductIdAndIsPreviewImageTrue(productId);

        productDetail.setDetailImages(detailImages);
        productDetail.setPreviewImages(previewImages);

        // options info
        // productOptionCombination -> combinationDetail -> options
        // 한 상품에 대한 모든 옵션 조합 가져오기
        List<ProductOptionCombination> combinations = optionCombinationRepository.findByProductId(productId);

        List<OptionDto> optionDtoList = new ArrayList<>();

        for (ProductOptionCombination combination : combinations) {
            List<Long> optionIds = combinationDetailRepository.findByProductOptionCombination(combination).stream()
                    .map(x -> x.getProductOption().getId())
                    .collect(Collectors.toList());

            List<ProductOption> productOptions = productOptionRepository.findByIdIn(optionIds);
            OptionDto optionDto = OptionDto.toDto(combination, productOptions);
            optionDtoList.add(optionDto);
        }

        productDetail.setOptionInfo(optionDtoList);

        // category info
        String categoryInfo = categoryService.getCategoryHierarchyByProduct(product);
        productDetail.setCategoryInfo(categoryInfo);

        return productDetail;
    }

    @Override
    public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
        List<Long> categoryIds = categoryRepository.findAllSubcategoryIds(categoryId);
        List<Product> products = productRepository.findAllByCategoryIdIn(categoryIds);
        return products.stream().map(ProductResponseDto::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : " + productId));

        return new ModelMapper().map(product, ProductResponseDto.class);
    }

    @Override
    public ProductOptionCombinationResponseDto getProductOptionCombinationById(Long combinationId) {
        return ProductOptionCombinationResponseDto.toDto(optionCombinationRepository.findById(combinationId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : " + combinationId)));
    }

}
