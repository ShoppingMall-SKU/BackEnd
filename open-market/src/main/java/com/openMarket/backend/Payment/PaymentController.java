package com.openMarket.backend.Payment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    //CRUD
    @PostMapping("/payments")
    public ResponseEntity<String> createPayment(@RequestBody PaymentDTO paymentDTO){
        paymentService.createPayment(paymentDTO.getMethod(), paymentDTO.getAmount(), paymentDTO.getOrdering());
        return ResponseEntity.ok("created payment!");
    }
    // Read userId Payment List
    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Payment>> getPaymentListByUserId(@PathVariable int userId){
        List<Payment> paymentList = paymentService.getPaymentListByUserId(userId);
        return ResponseEntity.ok(paymentList);
    }

}
