package com.openMarket.backend.Cart;


import com.openMarket.backend.Product.Product;
import com.openMarket.backend.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    //CRUD -> 장바구니

    // Create Cart
    public void createBasket(User user, Product product, int quantity){
        Cart cart = new Cart();
        cart.setQuantity(quantity);
        cart.setUser(user); // 구매자
        cart.setProduct(product); // 판매 상품
        this.cartRepository.save(cart);
    }
    // total 값 구하기


    // Read Cart
    public List<Cart> getList(int id){
        return this.cartRepository.findByUserId(id);
    }

    // Delete Cart
    public void deleteBasket(Cart cart){
        this.cartRepository.delete(cart);
    }
}
