package com.mealKit.backend.Payment;

import com.mealKit.backend.Ordering.Ordering;
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
