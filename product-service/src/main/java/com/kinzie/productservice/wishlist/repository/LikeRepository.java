package com.kinzie.productservice.wishlist.repository;

import com.kinzie.productservice.wishlist.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByUserIdAndLikeType(Long userId,String likeType);

    void deleteByUserIdAndConnectId(Long userId, Long target);
}
