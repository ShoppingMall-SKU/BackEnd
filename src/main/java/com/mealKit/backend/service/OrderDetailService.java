package com.mealKit.backend.service;


import com.mealKit.backend.dto.response.OrderDetailResponseDto;
import com.mealKit.backend.domain.Ordering;
import com.mealKit.backend.domain.Product;
import com.mealKit.backend.domain.OrderDetail;
import com.mealKit.backend.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public void create(Ordering ordering, Product product, Integer quantity, Integer price) {

        this.orderDetailRepository.save(OrderDetail.builder()
                        .ordering(ordering)
                        .price(price)
                        .product(product)
                        .shipStatus("결제 확인")
                        .quantity(quantity)
                .build());

    }

    public List<OrderDetail> getOrderDetailAll() {
        return this.orderDetailRepository.findAll();
    }

    public OrderDetail getOrderDetailById(int id) {
        return this.orderDetailRepository.findById(id).orElse(null);
    }



    public void updateShipStatus(OrderDetail orderDetail, String status) {
        orderDetail.setShipStatus(status);
        this.orderDetailRepository.save(orderDetail);
    }


    public void delete(OrderDetail orderDetail) {
        this.orderDetailRepository.delete(orderDetail);
    }

}
