package com.mealKit.backend.dto.response;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;

@Getter
@Setter
@JsonComponent
public class OrderingListResponseDTO {
    private String userEmail;
    private List<OrderDetailResponseDto> list;
    private Integer totalPrice;

}
