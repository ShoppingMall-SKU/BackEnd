package com.mealKit.backend.service;


import com.mealKit.backend.dto.OrderDetailDTO;
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

    public List<OrderDetailDTO> getOrderDetailByOrdering(Ordering ordering) {
        List<OrderDetailDTO> list = new ArrayList<>();
        List<OrderDetail> orderDetailList = this.orderDetailRepository.findByOrdering(ordering);

        for (OrderDetail od : orderDetailList) {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setProductName(od.getProduct().getName());
            orderDetailDTO.setQuantity(od.getQuantity());
            orderDetailDTO.setPrice(od.getPrice());
            orderDetailDTO.setShipStatus(od.getShipStatus());
            list.add(orderDetailDTO);
        }
        return list;
    }

    public void updateShipStatus(OrderDetail orderDetail, String status) {
        orderDetail.setShipStatus(status);
        this.orderDetailRepository.save(orderDetail);
    }


    public void delete(OrderDetail orderDetail) {
        this.orderDetailRepository.delete(orderDetail);
    }

}
