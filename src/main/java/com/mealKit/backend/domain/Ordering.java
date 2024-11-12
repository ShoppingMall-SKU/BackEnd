package com.mealKit.backend.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "ordering")
@DynamicUpdate
public class Ordering {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "receiver_name")
    private String receiverName;


    @Column(name = "receiver_phone")
    private String receiverPhone;


    @Column(name = "receiver_address")
    private String receiverAddress;

    @Setter
    @Column(name = "message")
    private String message;

    @Setter
    @Getter
    @Column(name = "order_date")
    private LocalDate orderDate;

    @Setter
    @Column(name = "total_price")
    private Integer totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Ordering(String receiverName,
                    String receiverPhone,
                    String receiverAddress,
                    String message, LocalDate orderDate,
                    Integer totalPrice,
                    User user) {
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.receiverAddress = receiverAddress;
        this.message = message;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.user = user;
    }
}
