package com.openMarket.backend.User;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name; // 성명

    private String password; //비번

    private String phone; // ex)010-1234-5678

    private String email; // id

    private String address; // 주소

    @Enumerated(value = EnumType.STRING)
    private role role;

    public enum role {
        ROLE_USER("사용자"),
        ROLE_ADMIN("관리자");

        private String role;

        role(String role) {this.role = role;}
    }

    private String refreshToken;
}
