package com.mealKit.backend.oauth2;


import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;

    @Builder
    public OAuth2Attribute(Map<String, Object> attributes, String attributeKey, String email, String name) {
        this.attributes = attributes;
        this.attributeKey = attributeKey;
        this.email = email;
        this.name = name;
    }

    public static OAuth2Attribute of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .attributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User
                .builder()
                .email(email)
                .name(name)
                .password("")
                .role(UserRole.ROLE_USER.toString())
                .build();
    }
}
