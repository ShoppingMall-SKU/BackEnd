package com.openMarket.backend.Cart;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/basket")
public class CartController {
    private final CartService cartService;

    @PostMapping("/create-basket")
    public ResponseEntity<String> postBasket(@RequestBody CartDto cartDto){
        cartService.createBasket(cartDto.getUser(),
                cartDto.getProduct(), cartDto.getQuantity());
        return ResponseEntity.ok("Basket created successfully!");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Cart>> getBasketListByUserId(@PathVariable int id){

        List<Cart> cartList = cartService.getList(id);
        return ResponseEntity.ok(cartList);
    }


}
