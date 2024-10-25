package com.mealKit.backend.domain;


import com.mealKit.backend.domain.enums.ProviderType;
import com.mealKit.backend.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "User")
@Getter
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String pid;

    @Setter
    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password; //비번

    @Column(name = "phone")
    private String phone;

    @Setter
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    private String refreshToken;

    @Column(name = "providertype")
    @Enumerated(value = EnumType.STRING)
    private ProviderType providerType;

    @Builder
    public User(String pid, String name, String password, String phone, String email, String address, String role, String providerType) {
        this.pid = pid;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = UserRole.toEntity(role);
        this.providerType = ProviderType.toEntity(providerType);
    }
}
