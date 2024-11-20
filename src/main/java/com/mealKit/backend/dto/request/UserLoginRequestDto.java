package com.mealKit.backend.dto.request;

import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    String email;
    String password;
}
