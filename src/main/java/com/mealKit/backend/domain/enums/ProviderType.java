package com.mealKit.backend.domain.enums;

import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;

public enum ProviderType {
    GOOGLE("구글"),
    NAVER("네이버"),
    NORMAL("일반");

    private String pt;

    ProviderType(String providerType) {
        this.pt = providerType;
    }

    public String getProviderType() {
        return this.pt;
    }

    static public ProviderType toEntity(String pt){
        if (pt.equals("GOOGLE")){
            return ProviderType.GOOGLE;
        }else{
            throw new CommonException(ErrorCode.INVALID_PROVIDER);
        }
    }
}
