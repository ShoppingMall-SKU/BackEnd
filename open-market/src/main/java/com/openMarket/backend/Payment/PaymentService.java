package com.openMarket.backend.Payment;

import com.openMarket.backend.Ordering.Ordering;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    //CRUD
    // Create Payment
    public void createPayment(String method, int amount, Ordering ordering){
        Payment payment = new Payment();
        payment.setMethod(method);
        payment.setAmount(amount);
        payment.setOrdering(ordering);
        payment.setPaymentDate(LocalDateTime.now());

        paymentRepository.save(payment);
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
    // userId로 Payment list 반환 -> 쿼리문 작성 (Test 필요)
    public List<Payment> getPaymentListByUserId(int userId){
        return this.paymentRepository.findPaymentByUserId(userId);
    }

    // Payment Update -> 필요한가??
    public void modifiedPayment(Payment payment, String method, int amount, Ordering ordering){
        payment.setMethod(method);
        payment.setAmount(amount);
        payment.setOrdering(ordering);

        paymentRepository.save(payment);
    }
    // Payment Delete -> 관리자가 필요
    public void deletePayment(Payment payment){
        paymentRepository.delete(payment);
    }
}
