package com.openMarket.backend.OrderDetail;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

@Getter
@Setter
@JsonComponent
public class OrderDetailDTO {
    private String productName;

    private String ShipStatus;

    private int quantity;

    private int price;
}
