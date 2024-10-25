package com.mealKit.backend.dto;

import com.mealKit.backend.domain.Product;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Data
@NoArgsConstructor
public class ProductPathDto {
    private String name;

    private String detail;

    private Integer price;

    private String img;

    private Integer sale;

    private Integer stock;


    private LocalDateTime create_date;

    private String brand;

    private Product.status status;

}
