package com.openMarket.backend.Order;


import com.openMarket.backend.Product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int orderQuantity;

    private int orderAmount;

    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
}
