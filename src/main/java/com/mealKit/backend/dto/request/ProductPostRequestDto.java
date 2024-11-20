package com.mealKit.backend.dto.request;

import com.mealKit.backend.domain.enums.ProductStatus;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Data
@NoArgsConstructor
public class ProductPostRequestDto {

    private String name;

    private String detail;

    private Integer price;

    private String img;

    private Integer sale;

    private Integer stock;

    private LocalDateTime create_date;

    private String brand;

    private ProductStatus status;


}
