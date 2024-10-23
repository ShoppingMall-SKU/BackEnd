package com.mealKit.backend.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.mealKit.backend.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotBlank(message = "이름은 필수 입력 사항 입니다.")
    private String name;

    //@NotBlank(message = "비밀번호를 입력하십시오.")
    private String password;

    private String username;

    private String phone;

    @NotBlank(message = "이메일을 입력하십시오.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    private String address;

    private String role;



}
