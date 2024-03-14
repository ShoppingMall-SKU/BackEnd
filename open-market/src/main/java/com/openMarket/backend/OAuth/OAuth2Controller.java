package com.openMarket.backend.OAuth;


import com.openMarket.backend.JWT.JwtService;
import com.openMarket.backend.JWT.JwtToken;
import com.openMarket.backend.User.User;
import com.openMarket.backend.User.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OAuth2Controller {
    @Autowired
    private final OAuth2UserService oAuth2UserService;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserService userService;
    @GetMapping("/oauth2/code/google")
    public ResponseEntity<String> login(OAuth2UserRequest request, HttpServletResponse response) throws IOException, ServletException {
//        JwtToken token =  jwtService.generateToken(request.getClientRegistration().getRegistrationId(), User.role.ROLE_USER);
//        response.setHeader("Authorization", "Bearer" + token.getAccessToken());
//        User user = (User) authentication.getPrincipal();
//        userService.updateRefreshToken(user, token.getRefreshToken());

        return ResponseEntity.ok("success");
    }

}
