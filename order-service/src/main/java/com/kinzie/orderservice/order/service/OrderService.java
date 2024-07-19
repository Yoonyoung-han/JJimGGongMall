package com.kinzie.orderservice.order.service;

import com.kinzie.orderservice.order.dto.OrderDto;
import com.kinzie.orderservice.order.dto.request.OrderRequestDto;
import com.kinzie.orderservice.order.dto.response.OrderDetailResponseDto;
import com.kinzie.orderservice.order.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(Long userId, OrderRequestDto request);

    List<OrderDto> getAllOrderList(Long userId);

    OrderDetailResponseDto getOrderDetail(Long userId, Long orderId);

    String cancelOrder(Long orderId);
}
