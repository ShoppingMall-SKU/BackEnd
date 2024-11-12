package com.mealKit.backend.domain;


import com.mealKit.backend.domain.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
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
    private LocalDateTime createDate;

    @Column(name = "brand")
    private String brand;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToMany(mappedBy = "product")
    private Set<Cart> carts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<OrderDetail> orderDetails = new LinkedHashSet<>();

    @PreUpdate
    public void preUpdate() {this.createDate = LocalDateTime.now();}

    @Builder
    public Product(String name,
                   String detail,
                   Integer price,
                   String img,
                   Integer sale,
                   Integer stock,
                   LocalDateTime create_date,
                   String brand,
                   ProductStatus status) {

        this.name = name;
        this.detail = detail;
        this.price = price;
        this.img = img;
        this.sale = sale;
        this.stock = stock;
        this.createDate = create_date;
        this.brand = brand;
        this.status = status;
    }
}
