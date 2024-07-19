package com.kinzie.productservice.wishlist.service;

import com.kinzie.productservice.product.dto.response.ProductResponseDto;
import com.kinzie.productservice.wishlist.dto.request.WishlistRequestDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WishlistService {
    @Transactional
    String addWishlist(Long userId, WishlistRequestDto request);

    List<ProductResponseDto> getWishlist(Long userId);

    @Transactional
    String removeWishlist(Long userId, WishlistRequestDto request);
}
