package com.mealKit.backend.oauth2;

import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.enums.ProviderType;
import com.mealKit.backend.domain.enums.UserRole;
import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import com.mealKit.backend.jwt.JwtUtil;
import com.mealKit.backend.redis.RedisService;
import com.mealKit.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        try {
            OAuth2User oAuth2User = super.loadUser(userRequest);

            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            final OAuth2Response oAuth2Response;
            final ProviderType providerType;

            if (registrationId.equals("google")){
                oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
                providerType = ProviderType.GOOGLE;
            }else {
                throw new CommonException(ErrorCode.AUTH_SERVER_USER_INFO_ERROR);
            }
            //log.info(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            String refreshToken = jwtUtil.createRefreshToken();
            String pid = oAuth2Response.getProviderId();
            redisService.save(pid, refreshToken);

            return new CustomOAuth2User(
                    userRepository.findByPid(pid)
                    .orElseGet(
                            () -> {
                                User user = User.builder()
                                        .pid(pid)
                                        .email(oAuth2Response.getEmail())
                                        .name(oAuth2Response.getName())
                                        .role(UserRole.ROLE_GUEST)
                                        .providerType(providerType)
                                        .refreshToken(refreshToken)
                                        .build();
                                userRepository.save(user);
                                return user;
                            }
                    ));
        } catch (Exception e) {
            throw new CommonException(ErrorCode.AUTH_SERVER_USER_INFO_ERROR);
        }
    }
}
