package com.mealKit.backend.dto.response;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

@Getter
@Setter
@JsonComponent
public class OrderDetailResponseDto {
    private Integer productName;

    private String ShipStatus;

    private Integer quantity;

    private Integer price;
}
