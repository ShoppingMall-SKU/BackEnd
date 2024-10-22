package com.mealKit.backend.JWT;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@Data
@Builder
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
