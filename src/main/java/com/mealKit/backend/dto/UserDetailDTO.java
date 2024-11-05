package com.mealKit.backend.dto;

import com.mealKit.backend.domain.enums.ProviderType;
import com.mealKit.backend.domain.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;


// 프로필 조회 목적
@Getter
public class UserDetailDTO {

    private String name;

    private String phone;

    private String email;

    private String zipcode;

    private String streetAdr;

    private String detailAdr;

    private ProviderType providerType;
    @Builder
    public UserDetailDTO(String name, String phone, String email, String address, String streetAdr,String detailAdr, UserRole role, ProviderType pt) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.zipcode = address;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
        this.providerType = pt;
    }
}
