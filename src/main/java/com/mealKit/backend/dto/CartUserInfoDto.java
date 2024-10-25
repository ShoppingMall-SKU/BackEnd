package com.mealKit.backend.dto;

import com.mealKit.backend.domain.Product;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class CartUserInfoDto {
    private String userEmail;
    private Product product;
    private Integer quantity;
}
