package com.openMarket.backend.OAuth;


import com.openMarket.backend.JWT.JwtService;
import com.openMarket.backend.JWT.JwtToken;
import com.openMarket.backend.User.User;
import com.openMarket.backend.User.UserRepository;
import com.openMarket.backend.User.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
