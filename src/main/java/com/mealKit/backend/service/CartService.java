package com.mealKit.backend.service;


import com.mealKit.backend.dto.CartUserInfoDto;
import com.mealKit.backend.domain.Product;
import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.Cart;
import com.mealKit.backend.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    //CRUD -> 장바구니

    // Create Cart
    public void createCart(User user, Product product, Integer quantity){
        this.cartRepository.save(Cart.builder()
                        .product(product)
                        .quantity(quantity)
                        .user(user)
                .build());
    }

    // Read Cart
    public List<CartUserInfoDto> getList(int id){ // test 완
        List<Cart> cartList = cartRepository.findByUserId(id);
        List<CartUserInfoDto> cartUserInfoDtoList = new ArrayList<>();

        for (Cart cart : cartList){
            CartUserInfoDto cartUserInfoDto = new CartUserInfoDto();
            cartUserInfoDto.setUserEmail(cart.getUser().getEmail());
            cartUserInfoDto.setProduct(cart.getProduct());
            cartUserInfoDto.setQuantity(cart.getQuantity());
            cartUserInfoDtoList.add(cartUserInfoDto);
        }
        return cartUserInfoDtoList;
    }

    // cartId로 Cart조회
    public Cart getCartById(int id){
        return this.cartRepository.findById(id);
    }

    // cartId로 userId 찾기
    public int getUserIdByCart(int cartId){
        Cart cart = getCartById(cartId);
        return  cart.getUser().getId();
    }

    // Update Cart
    public void modifiedCart(Cart cart, int quantity){
        cart.setQuantity(quantity);
        this.cartRepository.save(cart);
    }

    // Delete Cart
    public void deleteCart(int id){
        this.cartRepository.delete(getCartById(id));
    }

}
