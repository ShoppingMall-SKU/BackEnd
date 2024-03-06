package com.openMarket.backend.Ordering;


import com.openMarket.backend.OrderDetail.OrderDetail;
import com.openMarket.backend.OrderDetail.OrderDetailDTO;
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
    private int totalPrice;

}
