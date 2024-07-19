package com.kinzie.productservice.wishlist.service;

import com.kinzie.productservice.client.UserServiceClient;
import com.kinzie.productservice.common.domain.User;
import com.kinzie.productservice.product.domain.Product;
import com.kinzie.productservice.product.dto.response.ProductResponseDto;
import com.kinzie.productservice.product.repository.ProductRepository;
import com.kinzie.productservice.wishlist.domain.Like;
import com.kinzie.productservice.wishlist.dto.request.WishlistRequestDto;
import com.kinzie.productservice.wishlist.repository.LikeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WishlistServiceImpl implements WishlistService{


    private final LikeRepository likeRepository;

    private final ProductRepository productRepository;

    private final UserServiceClient userServiceClient;

    @Autowired
    public WishlistServiceImpl(LikeRepository likeRepository, ProductRepository productRepository,
                               UserServiceClient userServiceClient) {
        this.likeRepository = likeRepository;
        this.productRepository = productRepository;
        this.userServiceClient = userServiceClient;
    }

    @Transactional
    @Override
    public String addWishlist(Long userId, WishlistRequestDto request) {
        Like wishlist = Like.builder()
                .connectId(request.getProductId())
                .likeType("wishlist")
                .build();
        User user = User.of(userServiceClient.getUserById(userId));

        wishlist.setUser(user);

        likeRepository.save(wishlist);
        return "Product added to wishlist";
    }

    @Override
    public List<ProductResponseDto> getWishlist(Long userId) {
        List<Like> wishlist = likeRepository.findAllByUserIdAndLikeType(userId,"wishlist");
        // 1. wishlist에서 connectId를 추출하여 리스트로 만듭니다.
        List<Long> connectIds = wishlist.stream()
                .map(Like::getConnectId)
                .toList();

        // 2. ProductRepository를 사용하여 connectIds에 해당하는 Product 엔티티들을 찾습니다.
        List<Product> wishProducts = productRepository.findAllByIdIn(connectIds);

        return wishProducts.stream()
                .map(ProductResponseDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public String removeWishlist(Long userId, WishlistRequestDto request) {
        Long target = request.getProductId();
        likeRepository.deleteByUserIdAndConnectId(userId,target);

        return "Item removed from wishlist successfully.";
    }
}
