package com.openMarket.backend.Payment;

import com.openMarket.backend.Ordering.Ordering;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Data
@NoArgsConstructor
public class PaymentDTO {

    private String method;

    private int amount;

    private Ordering ordering;
}
