package com.mealKit.backend.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.jackson.JsonComponent;

@Getter
@Setter
@JsonComponent
public class UserLoginDTO {

    private String email;
    private String password;
}
