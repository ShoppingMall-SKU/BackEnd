package com.mealKit.backend.domain;


import com.mealKit.backend.domain.enums.ProviderType;
import com.mealKit.backend.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
@Getter
@DynamicUpdate
public class User {

    @Id
    @Column(name = "pid")
    private String pid;

    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "password")
    private String password; //비번

    @Setter
    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Setter
    @Column(name = "zipcode")
    private String zipcode;         // 우편 번호 zipcode

    @Setter
    private String streetAddress;   // 지번 주소

    @Setter
    private String detailAddress;   // 상세 주소

    @Setter
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(name = "refresh_token")
    @Setter
    private String refreshToken;

    @Column(name = "providertype")
    @Enumerated(value = EnumType.STRING)
    private ProviderType providerType;

    @Setter
    @Column(name = "use_yn")
    private String useYN;

    @Builder
    public User(String pid, String name, String password, String phone, String email, String zipcode, UserRole role, ProviderType providerType, String streetAddress, String detailAddress) {
        this.pid = pid;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.zipcode = zipcode;
        this.role = role;
        this.providerType = providerType;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.useYN = "Y";
    }


    @PrePersist
    private void generatePID() {
        UUID uuid = UUID.randomUUID();
        this.pid = String.valueOf(Math.abs(uuid.getMostSignificantBits()));
    }
}
