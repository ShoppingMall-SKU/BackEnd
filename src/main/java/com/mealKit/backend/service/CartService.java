package com.mealKit.backend.service;


import com.mealKit.backend.dto.response.CartResponseDto;
import com.mealKit.backend.domain.Product;
import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.Cart;
import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import com.mealKit.backend.repository.CartRepository;
import com.mealKit.backend.repository.ProductRepository;
import com.mealKit.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    //CRUD -> 장바구니

    /**
     * 장바구니 추가
     * @param pid 유저 Pid
     * @param productId 상품 아이디
     * @param quantity 수량
     */
    // FIXME 효율성 수정 필요
    @Transactional
    public void createCart(String pid, Integer productId, Integer quantity) {
        User user = userRepository.findByPid(pid).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        Product product = productRepository.findById(productId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        this.cartRepository.save(Cart.builder()
                        .product(product)
                        .quantity(quantity)
                        .user(user)
                .build());
    }

    /**
     * 장바구니 리스트 메소드
     * @param pid 유저 pid
     * @return cart DTO 반환
     */
    public List<CartResponseDto> getList(String pid){ // test 완
        return cartRepository
                .findByUserPid(pid)
                .stream().map(CartResponseDto::toEntity)
                .toList();
    }

//    // cartId로 Cart조회
//    public Cart getCartById(int id){
//        return this.cartRepository.findById(id);
//    }

    // Update Cart
    public void modifiedCart(Cart cart, int quantity){
        cart.setQuantity(quantity);
        this.cartRepository.save(cart);
    }

//    // Delete Cart
//    public void deleteCart(int id){
//        this.cartRepository.delete(getList(id));
//    }

}
