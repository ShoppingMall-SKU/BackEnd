package com.mealKit.backend.service;


import com.mealKit.backend.domain.*;
import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import com.mealKit.backend.redis.RedissonLockStockFacade;
import com.mealKit.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final CartRepository cartRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final RedissonLockStockFacade redissonLockStockFacade;

    final Integer shipCost = 0;

    // FIXME 결제 때 필요한게 결제자 정보 -> 주소, 이름, 전화번호를 새로 받아야함. (기본적으로 사용자의 정보에서 받아옴)
    // FIXME 결제는 무조건 성공으로 가정한다. (진짜로 결제 로직을 짜기엔 제약이 있음)
    /**
     * 결제를 실질적으로 담당
     * @param userPid 유저 PID
     * @param receiverAdr 수신자 주소
     * @param receiverNm 수신자 이름
     * @param receiverPn 수신자 전화번호
     * @return ture, false 로 성공하면 참, 저중에 하나라도 실패하면 거짓 반환
     */
    @Transactional
    public Boolean create(String userPid, String receiverAdr, String receiverNm, String receiverPn) {
        User user = userRepository.findByPid(userPid).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        List<Cart> cartList = cartRepository.findByUserIdAndCartUseYn(user.getId());
        log.info(cartList.toString());
        // 장바구니에 담긴 총 가격 계산
        Integer totalPrice = cartList.stream()
                .mapToInt(e -> (int) (e.getProduct().getSale() * 0.01 * e.getProduct().getPrice() * e.getQuantity()))
                .sum();

        Ordering ordering = Ordering
                .builder()
                .user(user)
                .orderDate(LocalDate.now())
                .receiverAddress(receiverAdr)
                .receiverName(receiverNm)
                .receiverPhone(receiverPn)
                .totalPrice(totalPrice)
                .build();

        List<OrderDetail> orderDetailList = cartList.stream()
                .map(cart -> toOrderDetail(ordering, cart))
                .toList();

        /*
         결제 로직
         */

        orderDetailList.forEach(orderDetail -> {
            try {
                redissonLockStockFacade.decrease(orderDetail.getProduct().getId(), orderDetail.getQuantity());
            } catch (InterruptedException e) {
                throw new CommonException(ErrorCode.valueOf(e.getMessage()));
            }
        });

        Payment payment = Payment.builder()
                .ordering(ordering)
                .paymentDate(LocalDateTime.now())
                .method("카드 결제")
                .amount(totalPrice + shipCost)
                .build();

        cartList.forEach(cart -> cart.setUseYn(false));

        orderingRepository.save(ordering);
        orderDetailRepository.saveAll(orderDetailList);
        paymentRepository.save(payment);

        return true;
    }

    @Transactional
    protected OrderDetail toOrderDetail(Ordering ordering, Cart cart) {

        return OrderDetail
                .builder()
                .product(cart.getProduct())
                .quantity(cart.getQuantity())
                .ordering(ordering)
                .price(cart.getProduct().getPrice() * cart.getQuantity())
                .shipStatus("결제 확인 중")
                .quantity(cart.getQuantity())
                .build();
    }

//    public void create(User user, String name, String phone, String address, String msg) {
//        Ordering ordering = new Ordering();
//        ordering.setUser(user);
//        ordering.setReceiverName(name);
//        ordering.setReceiverPhone(phone);
//        ordering.setReceiverAddress(address);
//        ordering.setMessage(msg);
//        ordering.setOrderDate(LocalDateTime.now());
//        orderingRepository.save(ordering);
//
//        List<Cart> cartList = cartRepository.findByUserId(user.getId());
//        int price = 0;
//        for (Cart c : cartList) {
//            price += (c.getProduct().getPrice() * c.getQuantity());
//            orderDetailService.create(
//                    ordering,
//                    c.getProduct(),
//                    c.getQuantity(),
//                    c.getProduct().getPrice() * c.getQuantity()
//            );
//
//        }
//
//        ordering.setTotalPrice(price);
//
//        this.orderingRepository.save(ordering);
//
//
//    }
//
//    public List<OrderingListDTO> getOrderingByUser(User user) {
//        List<Ordering> list = orderingRepository.findByUser(user);
//        List<OrderingListDTO> res = new ArrayList<>();
//
//        for(Ordering o : list) {
//            OrderingListDTO orderingListDTO = new OrderingListDTO();
//            orderingListDTO.setUserEmail(o.getUser().getEmail());
//            orderingListDTO.setTotalPrice(o.getTotalPrice());
//            orderingListDTO.setList(orderDetailService.getOrderDetailByOrdering(o));
//            res.add(orderingListDTO);
//
//        }
//
//        return res;
//    }


    public void delete(Ordering ordering) {
        this.orderingRepository.delete(ordering);
    }
}
