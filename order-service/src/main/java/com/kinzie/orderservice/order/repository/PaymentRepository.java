package com.kinzie.orderservice.order.repository;

import com.kinzie.orderservice.order.domain.Order;
import com.kinzie.orderservice.order.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Payment findByOrder(Order order);
}
