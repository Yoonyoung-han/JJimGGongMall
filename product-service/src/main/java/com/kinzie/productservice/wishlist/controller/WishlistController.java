package com.kinzie.productservice.wishlist.controller;

import com.kinzie.productservice.common.ApiResponse;
import com.kinzie.productservice.product.dto.response.ProductResponseDto;
import com.kinzie.productservice.wishlist.dto.request.WishlistRequestDto;
import com.kinzie.productservice.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{userId}")
    public ApiResponse<String> addWishlist(@PathVariable Long userId, @RequestBody WishlistRequestDto request){
        String result = wishlistService.addWishlist(userId, request);
        return ApiResponse.of(HttpStatus.CREATED,result);
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<ProductResponseDto>> getWishlist(@PathVariable Long userId){
        List<ProductResponseDto> result = wishlistService.getWishlist(userId);
        return ApiResponse.ok(result);
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> removeWishlist(@PathVariable Long userId, @RequestBody WishlistRequestDto request){
        String result = wishlistService.removeWishlist(userId,request);
        return ApiResponse.ok(result);
    }
}
