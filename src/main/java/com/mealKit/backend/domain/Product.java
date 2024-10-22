package com.mealKit.backend.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Product")
@Getter
@DynamicUpdate
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "detail")
    private String detail;

    @Column(name = "price")
    private Integer price;

    @Column(name = "img")
    private String img;

    @Column(name = "sale")
    private Integer sale;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "create_date")
    private LocalDateTime create_date;

    @Column(name = "brand")
    private String brand;

    @Column(name = "status")
    private status status;

    @OneToMany(mappedBy = "product")
    private Set<Cart> carts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @PreUpdate
    public void preUpdate() {this.create_date = LocalDateTime.now();}

    public enum status {
        STATUS_COLD("냉장"),
        STATUS_FROZEN("냉동"),
        STATUS_ROOM_TEMP("실온");

        private String status;

        status(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

    }

    @Builder
    public Product(String name,
                   String detail,
                   Integer price,
                   String img,
                   Integer sale,
                   Integer stock,
                   LocalDateTime create_date,
                   String brand,
                   Product.status status) {

        this.name = name;
        this.detail = detail;
        this.price = price;
        this.img = img;
        this.sale = sale;
        this.stock = stock;
        this.create_date = create_date;
        this.brand = brand;
        this.status = status;
    }
}
