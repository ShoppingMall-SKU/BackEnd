package com.mealKit.backend.oauth2;


import com.mealKit.backend.service.UserService;
import com.mealKit.backend.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.web.bind.annotation.GetMapping;
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
