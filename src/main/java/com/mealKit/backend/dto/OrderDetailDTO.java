package com.mealKit.backend.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

@Getter
@Setter
@JsonComponent
public class OrderDetailDTO {
    private String productName;

    private String ShipStatus;

    private Integer quantity;

    private Integer price;
}
