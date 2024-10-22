package com.mealKit.backend.OAuth;


import com.mealKit.backend.JWT.JwtService;
import com.mealKit.backend.User.User;
import com.mealKit.backend.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public OAuth2User loadUser (OAuth2UserRequest request) throws OAuth2AuthenticationException {
        org.springframework.security.oauth2.client.userinfo.OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(request);


        String registrationId = request.getClientRegistration().getRegistrationId();
        String userNameAttributeName = request
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();


        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(oAuth2Attribute);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())),
                oAuth2Attribute.getAttributes(),
                oAuth2Attribute.getAttributeKey());
    }

    private User saveOrUpdate(OAuth2Attribute attribute) {
        User user = userRepository.findByEmail(attribute.getEmail())
                .map(u ->
                        {
                            u.setName(attribute.getName());
                            u.setEmail(attribute.getEmail());
                            return u;
                        }
                )
                .orElse(attribute.toEntity());
        return userRepository.save(user);
    }


}
