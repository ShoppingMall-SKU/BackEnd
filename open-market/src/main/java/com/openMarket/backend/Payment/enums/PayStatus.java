package com.openMarket.backend.Payment.enums;

public enum PayStatus { // 결제 중 예외 발생 시 자동 결제 취소 기능
    SUCCESS("결제완료"),
    REFUND("환불완료"),
    WAITING_FOR_PAYMENT("결제대기");
    private String status;

    public String getStatus() {
        return status;
    }

    PayStatus(String status) {
        this.status = status;
    }
}
