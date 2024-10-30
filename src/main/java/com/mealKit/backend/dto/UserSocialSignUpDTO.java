package com.mealKit.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserSocialSignUpDTO {

    @NotBlank
    private String phone;

    @NotBlank(message = "우편번호는 필수 입력 값입니다.")
    private String zipcode;

    @NotBlank
    private String streetAdr;

    @NotBlank
    private String detailAdr;
}
