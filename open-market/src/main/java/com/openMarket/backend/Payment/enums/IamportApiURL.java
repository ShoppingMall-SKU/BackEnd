package com.openMarket.backend.Payment.enums;

public enum IamportApiURL { // IamPort의 API URL을 상수로 담은 열거형
    GET_TOKEN_URL("https://api.iamport.kr/users/getToken"),
    CANCEL_URL("https://api.iamport.kr/payments/cancel");
    private String url;

    public String getURL(){
        return url;
    }
    IamportApiURL(String url){
        this.url = url;
    }
}
