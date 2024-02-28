package com.openMarket.backend.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetResponseDto{
        private String name; // 성명

        private String password; //비번

        private String phone; // ex)010-1234-5678

        private String email; // id

        private String address; // 주소

        private User.role role;
    }
}
