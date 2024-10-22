package com.mealKit.backend.Ordering;


import com.mealKit.backend.Cart.Cart;
import com.mealKit.backend.Cart.CartRepository;
import com.mealKit.backend.OrderDetail.OrderDetailService;
import com.mealKit.backend.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final CartRepository cartRepository;
    private final OrderDetailService orderDetailService;

    public void create(User user, String name, String phone, String address, String msg) {
        Ordering ordering = new Ordering();
        ordering.setUser(user);
        ordering.setReceiverName(name);
        ordering.setReceiverPhone(phone);
        ordering.setReceiverAddress(address);
        ordering.setMessage(msg);
        ordering.setOrderDate(LocalDateTime.now());
        orderingRepository.save(ordering);

        List<Cart> cartList = cartRepository.findByUserId(user.getId());
        int price = 0;
        for (Cart c : cartList) {
            price += (c.getProduct().getPrice() * c.getQuantity());
            orderDetailService.create(
                    ordering,
                    c.getProduct(),
                    c.getQuantity(),
                    c.getProduct().getPrice() * c.getQuantity()
            );

        }

        ordering.setTotalPrice(price);

        this.orderingRepository.save(ordering);


    }

    public List<OrderingListDTO> getOrderingByUser(User user) {
        List<Ordering> list = orderingRepository.findByUser(user);
        List<OrderingListDTO> res = new ArrayList<>();

        for(Ordering o : list) {
            OrderingListDTO orderingListDTO = new OrderingListDTO();
            orderingListDTO.setUserEmail(o.getUser().getEmail());
            orderingListDTO.setTotalPrice(o.getTotalPrice());
            orderingListDTO.setList(orderDetailService.getOrderDetailByOrdering(o));
            res.add(orderingListDTO);

        }

        return res;
    }


    public void delete(Ordering ordering) {
        this.orderingRepository.delete(ordering);
    }
}
