package com.mealKit.backend.oauth2;

public interface OAuth2Response {

    // 제공자 ex) naver, google
    String getProvider();

    // 제공자에서 발급해주는 아이디(번호)
    String getPid();

    // 이메일
    String getEmail();

    // 사용자 실명(설정한 이름)
    String getName();
}
