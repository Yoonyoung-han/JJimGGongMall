package com.kinzie.orderservice.order.controller;

import com.kinzie.orderservice.common.ApiResponse;
import com.kinzie.orderservice.order.dto.OrderDto;
import com.kinzie.orderservice.order.dto.request.OrderRequestDto;
import com.kinzie.orderservice.order.dto.response.OrderDetailResponseDto;
import com.kinzie.orderservice.order.dto.response.OrderResponseDto;
import com.kinzie.orderservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ApiResponse<OrderResponseDto> generateOrder(@PathVariable Long userId, @RequestBody OrderRequestDto request){
        OrderResponseDto result = orderService.createOrder(userId, request);
        return ApiResponse.of(HttpStatus.CREATED,result);
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<OrderDto>> getAllOrderList(@PathVariable Long userId){
        List<OrderDto> result = orderService.getAllOrderList(userId);
        return ApiResponse.ok(result);
    }

    @GetMapping("/{userId}/{orderId}")
    public ApiResponse<OrderDetailResponseDto> getOrderDetail(@PathVariable Long userId, @PathVariable Long orderId){
        OrderDetailResponseDto response = orderService.getOrderDetail(userId, orderId);
        return ApiResponse.ok(response);
    }

    @PutMapping("/cancel-all/{orderId}")
    public ApiResponse<String> cancelOrder(@PathVariable Long orderId){
        String response = orderService.cancelOrder(orderId);
        return ApiResponse.ok(response);
    }
}
