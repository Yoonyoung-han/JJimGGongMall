package com.kinzie.orderservice.order.repository;

import com.kinzie.orderservice.order.domain.Order;
import com.kinzie.orderservice.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    List<OrderItem> findByOrder(Order order);
}
