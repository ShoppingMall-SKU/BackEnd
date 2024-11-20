package com.mealKit.backend.dto.response;

import com.mealKit.backend.domain.Ordering;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Data
@NoArgsConstructor
public class PaymentResponseDto {

    private String method;

    private Integer amount;

    private Ordering ordering;
}
