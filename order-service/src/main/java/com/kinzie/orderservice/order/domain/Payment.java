package com.kinzie.orderservice.order.domain;

import com.kinzie.orderservice.common.BaseEntity;
import com.kinzie.orderservice.order.domain.constant.PaymentStatus;
import com.kinzie.orderservice.order.domain.constant.PgPaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PgPaymentStatus pgStatus;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private BigDecimal amount;
    private int installmentMonths;
    private String paymentMethod;
    private String paymentDetails;
    private String cardIssuer;
    private String pgProvider;
    private String pgTransactionId;
    private LocalDateTime approvalDate;

    @Builder
    public Payment(PaymentStatus paymentStatus, PgPaymentStatus pgStatus, Order order, BigDecimal amount, int installmentMonths,
                   String paymentMethod, String paymentDetails, String cardIssuer, String pgProvider, String pgTransactionId,
                   LocalDateTime approvalDate) {
        this.paymentStatus = paymentStatus;
        this.pgStatus = pgStatus;
        this.order = order;
        this.amount = amount;
        this.installmentMonths = installmentMonths;
        this.paymentMethod = paymentMethod;
        this.paymentDetails = paymentDetails;
        this.cardIssuer = cardIssuer;
        this.pgProvider = pgProvider;
        this.pgTransactionId = pgTransactionId;
        this.approvalDate = approvalDate;
    }



    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
