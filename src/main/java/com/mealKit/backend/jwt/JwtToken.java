package com.mealKit.backend.jwt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@AllArgsConstructor
@Data
@Builder
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
