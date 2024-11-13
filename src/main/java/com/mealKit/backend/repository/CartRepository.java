package com.mealKit.backend.repository;

import com.mealKit.backend.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer>{
    List<Cart> findByUserId(int id);
    Cart findById(int cartId);


    @Query(value = "SELECT * FROM mealkit.cart WHERE user_id = ?1 AND use_yn = TRUE", nativeQuery = true)
    List<Cart> findByUserIdAndCartUseYn(Integer UserId);

}
