package com.mealKit.backend.OrderDetail;


import com.mealKit.backend.Ordering.Ordering;
import com.mealKit.backend.Product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public void create(Ordering ordering, Product product,  int quantity, int price) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrdering(ordering);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(quantity);
        orderDetail.setShipStatus("결제 확인 중");
        orderDetail.setPrice(price);

        this.orderDetailRepository.save(orderDetail);

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
