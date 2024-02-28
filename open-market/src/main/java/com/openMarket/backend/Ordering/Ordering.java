package com.openMarket.backend.Ordering;


import com.openMarket.backend.Payment.Payment;
import com.openMarket.backend.Product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ordering")
public class Ordering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int orderQuantity;

    private int orderAmount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(mappedBy = "ordering")
    private Payment payment;


}
