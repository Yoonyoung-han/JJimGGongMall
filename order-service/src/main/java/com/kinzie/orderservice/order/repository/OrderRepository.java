package com.kinzie.orderservice.order.repository;

import com.kinzie.orderservice.order.domain.Order;
import com.kinzie.orderservice.order.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByUserAndId(User user, Long orderId);

    List<Order> findByUser(User user);
}
