package com.mealKit.backend.jwt;


import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.enums.ProviderType;
import com.mealKit.backend.domain.enums.UserRole;
import com.mealKit.backend.oauth2.CustomOAuth2User;
import com.mealKit.backend.security.Constant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private static final List<String> NO_CHECK_LIST = new ArrayList<>();
    private static final String NO_CHECK_URL = "/api/user/login";
    private static final String NO_CHECK_URL2 = "/api/user/signup";

    private static final String NO_CHECK_URL3 = "/api/product";

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(request.getRequestURI());
        if(Arrays.stream(Constant.allowedUrls).toList().contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println("cookie name : " + cookie.getName());
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        // Authorization 헤더 검증
        if (authorization == null) {
            System.out.println("token null" + request.getRequestURI());
            filterChain.doFilter(request, response);

            return;
        }

        // 토큰
        String token = authorization;

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request,response);

            return;
        }

        //토큰에서 pid, role 획득
        String pid = jwtUtil.getPid(token);
        String role = jwtUtil.getRole(token);
        String providerType = jwtUtil.getProviderType(token);


        // id, pw 로그인 회원
        if(providerType.isEmpty()) {
//            log.info("provider type is empty");
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            pid,
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority(role)));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 소셜 로그인 회원
        else {
            // user를 생성하여 값 set
            User user = User.builder()
                    .pid(pid)
                    .providerType(ProviderType.toEntity(providerType))
                    .role(UserRole.toEntity(role))
                    .build();

            // UserDetails에 회원 정보 객체 담기
            CustomOAuth2User customOAuth2User = new CustomOAuth2User(user);

            // 스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, "", customOAuth2User.getAuthorities());

            // 세션에 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request,response);

    }

    private String resolveToken(HttpServletRequest request) {
        String BearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(BearerToken) && BearerToken.startsWith("Bearer")) {
            BearerToken  = BearerToken.replace("Bearer ", "");
            return BearerToken;
        }
        return null;
    }

}
