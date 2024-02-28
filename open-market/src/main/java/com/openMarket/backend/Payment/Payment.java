package com.openMarket.backend.Payment;


import com.openMarket.backend.Order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String method;

    private int amount;

    private LocalDateTime paymentDate;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
