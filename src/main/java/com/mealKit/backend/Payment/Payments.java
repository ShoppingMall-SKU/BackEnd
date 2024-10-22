package com.mealKit.backend.Payment;


import com.mealKit.backend.Ordering.Ordering;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "payment")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String method;

    private int amount; // 배송비 포함 가격

    private LocalDateTime paymentDate;

    @OneToOne
    @JoinColumn(name = "ordering_id")
    private Ordering ordering;
}
