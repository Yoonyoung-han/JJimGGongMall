package com.kinzie.orderservice.order.service;

import com.kinzie.orderservice.client.ProductServiceClient;
import com.kinzie.orderservice.client.UserServiceClient;
import com.kinzie.orderservice.common.dto.response.ProductResponseDto;
import com.kinzie.orderservice.common.exception.CustomLogicException;
import com.kinzie.orderservice.common.exception.ErrorCode;
import com.kinzie.orderservice.order.domain.*;
import com.kinzie.orderservice.order.domain.constant.DeliveryStatus;
import com.kinzie.orderservice.order.domain.constant.OrderStatus;
import com.kinzie.orderservice.order.domain.constant.PaymentStatus;
import com.kinzie.orderservice.order.domain.constant.PgPaymentStatus;
import com.kinzie.orderservice.order.dto.OrderDto;
import com.kinzie.orderservice.order.dto.OrderItemDto;
import com.kinzie.orderservice.order.dto.request.OrderRequestDto;
import com.kinzie.orderservice.order.dto.response.OrderDetailResponseDto;
import com.kinzie.orderservice.order.dto.response.OrderResponseDto;
import com.kinzie.orderservice.order.repository.OrderItemRepository;
import com.kinzie.orderservice.order.repository.OrderRepository;
import com.kinzie.orderservice.order.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentRepository paymentRepository;
    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        PaymentRepository paymentRepository, UserServiceClient userServiceClient,
                        ProductServiceClient productServiceClient) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.paymentRepository = paymentRepository;
        this.userServiceClient = userServiceClient;
        this.productServiceClient = productServiceClient;
    }

    @Transactional
    public OrderResponseDto createOrder(Long userId, OrderRequestDto request) {
        BigDecimal totalPrice = new BigDecimal(0);

        Long addressId = request.getDeliveryAddressId();
        Address address = Address.of(userServiceClient.getAddressById(addressId));
        User user = User.of(userServiceClient.getUserById(userId));
        // 연관 관계 맵핑
        user.addAddress(address);

        List<OrderItemDto> orderItems = request.getOrderItems();

        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDto orderItem : orderItems){
            Long productId = orderItem.getProductId();
            Long combinationId = orderItem.getOptionCombinationId();
            Long orderQuantity = orderItem.getQuantity();
            ProductResponseDto prductDto = productServiceClient.getProductById(productId);

            Product product = Product.of(prductDto);
            Category category = Category.of(productServiceClient.getCategoryById(prductDto.getCategoryId()));
            product.setCategory(category);

            ProductOptionCombination combination = ProductOptionCombination.of(
                    productServiceClient.getProductOptionCombinationById(combinationId));
            combination.setProduct(product);


            // 재고 줄이기
            product.decrease(orderQuantity);
            combination.decrease(orderQuantity);

            // orderItem
            OrderItem orderItemObj = OrderItem.builder()
                    .price(combination.getPrice())
                    .quantity(orderQuantity)
                    .shippingAddress(address)
                    .product(product)
                    .optionCombination(combination)
                    .deliveryStatus(DeliveryStatus.NOT_READY)
                    .refundCheck(true)
                    .build();

            orderItemList.add(orderItemObj);
            totalPrice = totalPrice.add(combination.getPrice());
        }

        Order order = Order.builder()
                .user(user)
                .discountAmount(BigDecimal.valueOf(0))
                .orderStatus(OrderStatus.CREATED)
                .totalAmount(totalPrice)
                .build();

        order.generateOrderNumber(userId);
        order.addAllOrderDetail(orderItemList);

        orderRepository.save(order);

        // payment 저장
        OrderRequestDto.PaymentInfo paymentInfo = request.getPaymentInfo();

        Payment payment = Payment.builder()
                .amount(totalPrice)
                .order(order)
                .paymentStatus(PaymentStatus.INITIALIZED)
                .cardIssuer(paymentInfo.getCardIssuer())
                .installmentMonths(paymentInfo.getInstallmentMonth())
                .paymentMethod(paymentInfo.getPaymentMethod())
                .paymentDetails(paymentInfo.getPaymentDetails())
                .pgStatus(PgPaymentStatus.REQUESTED)
                .build();

        paymentRepository.save(payment);

        OrderResponseDto response =  OrderResponseDto.builder()
                .orderId(order.getId())
                .orderStatus(OrderStatus.CREATED.getDescription())
                .customerId(userId)
                .deliveryAddress(address.getSingleLineAddress())
                .build();

        response.addProductList(orderItemList);

        return response;
    }


    @Transactional
    @Override
    public List<OrderDto> getAllOrderList(Long userId) {

        User user = User.of(userServiceClient.getUserById(userId));

        List<OrderDto> result = new ArrayList<>();
        List<Order> orderList = orderRepository.findByUser(user);

        for (Order order : orderList){
            OrderDto orderDto = OrderDto.builder()
                    .orderNumber(order.getOrderNumber())
                    .totalAmount(order.getTotalAmount())
                    .orderStatus(order.getOrderStatus().getDescription())
                    .orderItemList(order.getOrderItemList().stream().map(OrderItemDto::toDto).toList())
                    .build();
            result.add(orderDto);
        }

        return result;
    }

    @Transactional
    public OrderDetailResponseDto getOrderDetail(Long userId, Long orderId) {
        User user = User.of(userServiceClient.getUserById(userId));

        Order order = orderRepository.findByUserAndId(user,orderId);

        List<OrderItem> orderItemList = orderItemRepository.findByOrder(order);

        OrderDetailResponseDto result = OrderDetailResponseDto.builder()
                .orderDate(order.getCreatedAt())
                .paymentId(order.getPayment().getId())
                .orderStatus(order.getOrderStatus().getDescription())
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .build();

        result.addOrderItems(orderItemList);

        return result;
    }

    @Transactional
    public String cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : "+ orderId));

        List<OrderItem> orderItemList = orderItemRepository.findByOrder(order);

        for (OrderItem orderItem : orderItemList) {
            // 배송 상태 확인
            if (orderItem.getDeliveryStatus().equals(DeliveryStatus.DELIVERED) ||
                    orderItem.getDeliveryStatus().equals(DeliveryStatus.IN_TRANSIT)) {
                throw CustomLogicException.createBadRequestError(ErrorCode.NON_CANCELLABLE_PRODUCT);
            }

            // 상품 및 옵션 조회
            Long productId = orderItem.getProduct().getId();
            Long combinationId = orderItem.getOptionCombination().getId();
            Long orderQuantity = orderItem.getQuantity();

            ProductResponseDto prductDto = productServiceClient.getProductById(productId);
            Product product = Product.of(prductDto);
            Category category = Category.of(productServiceClient.getCategoryById(prductDto.getCategoryId()));
            product.setCategory(category);


            ProductOptionCombination combination = ProductOptionCombination.of(
                    productServiceClient.getProductOptionCombinationById(combinationId));
            combination.setProduct(product);

            // 재고 증가
            product.increase(orderQuantity);
            combination.increase(orderQuantity);

            // 주문 항목 상태 업데이트
            orderItem.setOrderStatus(OrderStatus.CANCELLED);
            orderItem.setRefundCheck(true);
        }

        // 결제 조회 및 상태 업데이트
        Payment payment = paymentRepository.findByOrder(order);
        payment.setPaymentStatus(PaymentStatus.CANCELLED);

        // 주문 상태 업데이트
        order.setOrderStatus(OrderStatus.CANCELLED);

        // 주문 및 결제 상태 저장
        orderRepository.save(order);
        paymentRepository.save(payment);

        return "Order and payment cancelled successfully.";
    }
}
