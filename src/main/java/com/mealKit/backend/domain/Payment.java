package com.mealKit.backend.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@DynamicUpdate
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "method")
    private String method;

    @Column(name = "amount")
    private Integer amount; // 배송비 포함 가격

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Ordering ordering;

    @Builder
    public Payment(String method, Integer amount, LocalDateTime paymentDate, Ordering ordering) {
        this.method = method;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.ordering = ordering;
    }

}
