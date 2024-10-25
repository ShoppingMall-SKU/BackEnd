package com.mealKit.backend.oauth2;

import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.enums.UserRole;
import com.mealKit.backend.dto.CustomOAuth2User;
import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import com.mealKit.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User" + oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final OAuth2Response oAuth2Response;

        if (registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }else {
            throw new CommonException(ErrorCode.AUTH_SERVER_USER_INFO_ERROR);
        }

        String pid = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        return new CustomOAuth2User(
                userRepository.findByPid(pid)
                .orElseGet(
                        () -> {
                            User user = User.builder()
                                    .pid(pid)
                                    .email(oAuth2Response.getEmail())
                                    .name(oAuth2Response.getName())
                                    .role("ROLE_USER")
                                    .build();
                            userRepository.save(user);
                            return user;
                        }
                ));
    }
}
