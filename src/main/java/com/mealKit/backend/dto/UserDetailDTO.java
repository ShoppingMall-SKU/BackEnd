package com.mealKit.backend.dto;

import com.mealKit.backend.domain.enums.ProviderType;
import com.mealKit.backend.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UserDetailDTO {

    private String name;

    @Setter
    @Length(min=8, max=16, message="비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String password;

    private String pid;

    @Setter
    @NotBlank(message = "전화번호를 입력하십시오.")
    private String phone;

    private String email;

    @Setter
    @NotEmpty(message = "우편번호는 필수 입력 값입니다.")
    private String zipcode;

    @Setter
    private String streetAdr;

    @Setter
    private String detailAdr;

    @Setter
    private UserRole role;

    private ProviderType providerType;
    @Builder
    public UserDetailDTO(String name, String password, String pid, String phone, String email, String address, String streetAdr,String detailAdr, UserRole role, ProviderType pt) {
        this.name = name;
        this.password = password;
        this.pid = pid;
        this.phone = phone;
        this.email = email;
        this.zipcode = address;
        this.streetAdr = streetAdr;
        this.detailAdr = detailAdr;
        this.role = role;
        this.providerType = pt;
    }
}
