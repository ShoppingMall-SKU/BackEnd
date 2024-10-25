package com.mealKit.backend.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;

@Getter
@Setter
@JsonComponent
public class OrderingListDTO {
    private String userEmail;
    private List<OrderDetailDTO> list;
    private Integer totalPrice;

}
