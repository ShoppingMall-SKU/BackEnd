package com.mealKit.backend.oauth2;

import com.mealKit.backend.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


public class CustomOAuth2User implements OAuth2User {

    private final User user;

    public CustomOAuth2User(User user){
        this.user = user;
    }

    // 구글, 네이버 형식이 달라 사용하기 힘들어 직접 구현
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of("pid", user.getPid(), "name", user.getName(), "email", user.getEmail());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().name();
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return user.getName();
    }

    public String getPid() {
        return user.getPid();
    }

    public String getProviderType() { return user.getProviderType().name();}

    public String getEmail() {return user.getEmail();}
}
