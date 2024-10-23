package com.mealKit.backend.domain;


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

    private String username;
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
    private role role;

    public enum role {
        ROLE_USER("사용자"),
        ROLE_ADMIN("관리자");
        private String role;

        role(String role) {this.role = role;}
    }

    private String refreshToken;

    //private providerType providerType;

    public enum providerType {
        GOOGLE("구글"),
        NORMAL("일반");

        private String pt;

        providerType(String providerType) {
            this.pt = providerType;
        }

        public String getProviderType() {
            return this.pt;
        }
    }

    @Builder
    public User(String name, String password, String phone, String email, String address, User.role role) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
    }
}
