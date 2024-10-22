package com.mealKit.backend.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer>{
    List<Cart> findByUserId(int id);
    Cart findById(int cartId);

}
