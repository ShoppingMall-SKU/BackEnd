package com.openMarket.backend.OrderDetail;


import com.openMarket.backend.Ordering.Ordering;
import com.openMarket.backend.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime orderDate;

    private String receiverName;

    private String receiverPhone;

    private String receiverAddress;

    private String message;

    private String shipStatus;


    @ManyToOne
    @JoinColumn(name = "ordering_id")
    private Ordering ordering;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
