package com.mealKit.backend.repository;

import com.mealKit.backend.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer>{
    List<Cart> findByUserPid(String pid);
    Optional<Cart> findById(Integer cartId);

    @Query(value = "SELECT * FROM mealkit.cart WHERE user_pid = ?1 AND use_yn = TRUE", nativeQuery = true)
    List<Cart> findByUserIdAndCartUseYn(String UserPid);
}
