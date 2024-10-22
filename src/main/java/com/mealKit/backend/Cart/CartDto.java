package com.mealKit.backend.Cart;

import com.mealKit.backend.User.User;
import com.mealKit.backend.Product.Product;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class CartDto {
    private int quantity;
    private User user;
    private Product product;
}
