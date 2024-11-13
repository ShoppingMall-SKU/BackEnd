package com.mealKit.backend.jwt;


import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.enums.ProviderType;
import com.mealKit.backend.domain.enums.UserRole;
import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import com.mealKit.backend.interceptor.Pid;
import com.mealKit.backend.oauth2.CustomOAuth2User;
import com.mealKit.backend.redis.RedisConfig;
import com.mealKit.backend.redis.RedisService;
import com.mealKit.backend.repository.UserRepository;
import com.mealKit.backend.security.Constant;
import com.mealKit.backend.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Filter;
import org.springframework.context.annotation.ComponentScan;
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

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private static final List<String> NO_CHECK_LIST = new ArrayList<>();
    private final RedisService redisService;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info(request.getRequestURI());
        if (Constant.allowedUrls.stream().anyMatch(request.getRequestURI()::startsWith)) {
            filterChain.doFilter(request, response); // 허용된 URL이면 필터 체인 계속 진행
            return;
        }
        // 토큰
        String token = resolveToken(request);

        log.info("----------------" + request.getHeader("Authorization"));

        try {
            //토큰에서 pid, role 획득
            String pid = jwtUtil.getPid(token);
            String role = jwtUtil.getRole(token);
            String providerType = jwtUtil.getProviderType(token);

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
        } catch (JwtException e) {

            String pid = jwtUtil.getPid(token);

            User user = userRepository.findByPid(pid).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

            String providerType = user.getProviderType().getProviderType();
            String role = user.getRole().getRole();

            System.out.println("token expired.. regenerating...");
            try{
                redisService.findByKey(pid);
                Cookie cookie = new Cookie("Authorization",jwtUtil.createJwt(
                        pid,
                        providerType,
                        role,
                        60 * 60 *60L
                ));
                cookie.setMaxAge(60*60*60);

                cookie.setSecure(true);
                cookie.setPath("/");
                cookie.setHttpOnly(false);
                response.addCookie(cookie);

                filterChain.doFilter(request,response);
                return;
            } catch(Exception ec2){
                throw new CommonException(ErrorCode.ACCESS_DENIED_ERROR);
            }
        }
//
//        // id, pw 로그인 회원
//        if(providerType.isEmpty()) {
////            log.info("provider type is empty");
//            Authentication authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            pid,
//                            null,
//                            Collections.singletonList(new SimpleGrantedAuthority(role)));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        // 소셜 로그인 회원
//        else {
//            // user를 생성하여 값 set
//            User user = User.builder()
//                    .pid(pid)
//                    .providerType(ProviderType.toEntity(providerType))
//                    .role(UserRole.toEntity(role))
//                    .build();
//
//            // UserDetails에 회원 정보 객체 담기
//            CustomOAuth2User customOAuth2User = new CustomOAuth2User(user);
//
//            // 스프링 시큐리티 인증 토큰 생성
//            Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, "", customOAuth2User.getAuthorities());
//
//            // 세션에 사용자 등록
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//        }

//        filterChain.doFilter(request,response);

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
