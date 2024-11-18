package com.mealKit.backend.repository;

import com.mealKit.backend.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    // UserId를 이용해 Payment list 반환 쿼리문 작성 필요
    @Query("select p from Payment as p, User as u where u.pid = p.ordering.user.pid")
    List<Payment> findPaymentByUserId(String userPid);

    Payment findPaymentById(int id);
    Payment findPaymentByOrderingId(int orderingId);
}
