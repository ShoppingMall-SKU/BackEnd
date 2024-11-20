package com.mealKit.backend.dto.response;

import com.mealKit.backend.domain.Cart;
import com.mealKit.backend.domain.Product;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class CartResponseDto {
    private Integer id;
    private String img;
    private String name;
    private Integer price;
    private Integer quantity;
    private Long totalPrice;


    @Builder
    public CartResponseDto(Integer id, String img, String name, Integer price, Integer quantity, Long totalPrice) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public static CartResponseDto toEntity(Cart cart) {
        return CartResponseDto.builder()
                .id(cart.getId())
                .img(cart.getProduct().getImg())
                .name(cart.getProduct().getName())
                .price(cart.getProduct().getPrice())
                .quantity(cart.getQuantity())
                .totalPrice(cart.getQuantity() * cart.getProduct().getDiscountPrice())
                .build();

    }
}
