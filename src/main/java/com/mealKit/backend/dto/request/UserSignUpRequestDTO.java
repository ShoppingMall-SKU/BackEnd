package com.mealKit.backend.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

// 폼 로그인 목적
@Getter
public class UserSignUpRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    @NotBlank
    private String zipcode;

    @NotBlank
    private String streetAdr;

    @NotBlank
    private String detailAdr;

    @Builder
    public UserSignUpRequestDTO(String name, String password, String phone, String email, String zipcode, String streetAdr, String detailAdr) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.zipcode = zipcode;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
    }
}
