package com.mealKit.backend.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


/*
    TODO : 관계 매핑은 맨 아래로, 일반 컬럼들은 중간, 주 키는 맨 위로 지정.
*/

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_detail")
@Getter
@DynamicUpdate
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @Column(name = "ship_status")
    private String shipStatus;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Ordering ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public OrderDetail(String shipStatus, Integer quantity, Integer price, Ordering ordering, Product product) {
        this.shipStatus = shipStatus;
        this.quantity = quantity;
        this.price = price;
        this.ordering = ordering;
        this.product = product;
    }
}
