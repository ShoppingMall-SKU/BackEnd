package com.mealKit.backend.service;

import com.mealKit.backend.domain.OrderDetail;
import com.mealKit.backend.repository.OrderDetailRepository;
import com.mealKit.backend.domain.Ordering;
import com.mealKit.backend.domain.Payment;
import com.mealKit.backend.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderDetailRepository orderDetailRepository;
    public void calculateStock(Payment payments){
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrdering(payments.getOrdering());
        for(OrderDetail orderDetail : orderDetailList){
            int quantity = orderDetail.getQuantity();
            int stock = orderDetail.getProduct().getStock();
            String name = orderDetail.getProduct().getName();
            if(stock - quantity < 0){
                throw new IllegalArgumentException(name + " : 상품 재고가 부족합니다.");
            }
        }
    }
    //CRUD
    // Create Payment
    public void createPayment(String method, int amount, Ordering ordering){
//        Payment payments = new Payment();
//        payments.setMethod(method);
//        payments.setAmount(amount);
//        calculateStock(payments); // 재고확인 예외처리
//        payments.setOrdering(ordering);
//        payments.setPaymentDate(LocalDateTime.now());

//        paymentRepository.save(payments);
    }

    // Read Payment
    // paymentId로 Payment 반환
    public Payment getPaymentById(int id){
        return this.paymentRepository.findPaymentById(id);
    }

    // OrderingId로 Payment 반환
    public Payment getPaymentByOrderingId(int orderingId){
        return this.paymentRepository.findPaymentByOrderingId(orderingId);
    }
//    // userId로 Payment list 반환 -> 쿼리문 작성 (Test 필요)
//    public List<Payment> getPaymentListByUserId(int userId){
//        return this.paymentRepository.findPaymentByUserId(userId);
//    }

//    // Payment Update -> 필요한가??
//    public void modifiedPayment(Payment payments, String method, int amount, Ordering ordering){
//        payments.setMethod(method);
//        payments.setAmount(amount);
//        payments.setOrdering(ordering);
//
//        paymentRepository.save(payments);
//    }
    // Payment Delete -> 관리자가 필요
    public void deletePayment(Payment payments){
        paymentRepository.delete(payments);
    }
}
