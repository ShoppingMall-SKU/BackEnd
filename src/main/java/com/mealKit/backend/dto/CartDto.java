package com.mealKit.backend.dto;

import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.Product;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class CartDto {
    private Integer quantity;
    private User user;
    private Product product;
}
