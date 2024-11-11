package com.mealKit.backend.dto;

import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.enums.ProviderType;
import com.mealKit.backend.domain.enums.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// 프로필 조회 목적
@Data
@NoArgsConstructor
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

    public static UserDetailDTO toEntity(User user) {
        return UserDetailDTO
                .builder()
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(user.getAddress())
                .detailAdr(user.getDetailAddress())
                .pt(user.getProviderType())
                .build();
    }
}
