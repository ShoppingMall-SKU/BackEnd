package com.mealKit.backend.oauth2;


import com.mealKit.backend.service.UserService;
import com.mealKit.backend.jwt.JwtService;
import com.mealKit.backend.jwt.JwtToken;
import com.mealKit.backend.domain.User;
import com.mealKit.backend.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getName();


        String role = oAuth2User.getAuthorities().stream()
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .getAuthority();

        log.info(role);


    }

    private void LoginSuccess(HttpServletResponse response, Authentication authentication) {
        JwtToken token = jwtService.generateToken(authentication, User.role.ROLE_USER);
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        if (user != null) {
            userService.updateRefreshToken(user, token.getRefreshToken());
            response.setHeader("Authorization", "Bearer " + token.getAccessToken());
        }
    }
}
