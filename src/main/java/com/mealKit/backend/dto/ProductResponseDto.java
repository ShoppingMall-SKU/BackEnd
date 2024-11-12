package com.mealKit.backend.dto;

import com.mealKit.backend.domain.Product;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class ProductResponseDto {
    private Integer id;

    private String name;

    private String detail;

    private Integer price;

    private String img;

    private Integer sale;

    private Integer stock;


    private LocalDateTime create_date;

    private String brand;

    private String status;

    @Builder
    public ProductResponseDto(Integer id, String name, String detail, Integer price, String img, Integer sale, Integer stock, LocalDateTime create_date, String brand, String status) {
        this.id = id;
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

    public static ProductResponseDto toEntity(Product product) {
        return ProductResponseDto
                .builder()
                .id(product.getId())
                .name(product.getName())
                .detail(product.getDetail())
                .price(product.getPrice())
                .img(product.getImg())
                .sale(product.getSale())
                .stock(product.getStock())
                .create_date(product.getCreateDate())
                .brand(product.getBrand())
                .status(product.getStatus().getStatus())
                .build();
    }


}
