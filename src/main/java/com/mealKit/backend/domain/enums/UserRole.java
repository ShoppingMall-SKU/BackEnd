package com.mealKit.backend.domain.enums;

import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;

public enum UserRole {
    ROLE_GUEST("신규"),
    ROLE_USER("사용자"),
    ROLE_ADMIN("관리자");
    private String role;

    UserRole(String role) {this.role = role;}

    public String getRole() {
        return this.role;
    }

    static public UserRole toEntity(String role){
        if (role.equals("ROLE_USER")){
            return UserRole.ROLE_USER;
        }else if (role.equals("ROLE_ADMIN")){
            return UserRole.ROLE_ADMIN;
        } else if (role.equals("ROLE_GUEST")) {
            return UserRole.ROLE_GUEST;
        } else{
           throw new CommonException(ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH);
        }
    }
}
