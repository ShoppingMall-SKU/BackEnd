package com.mealKit.backend.security;

import java.util.List;

/**
 * @serial
 */
public class Constant {

    static final List<String> ALLOWED_DOMAINS = List.of(
            "https://www.mealshop.shop",
            "https://mealshop-shop.vercel.app",
            "http://localhost:8080",
            "http://localhost:3000"
    );


    /**
     * 시큐리티 인증 생략 url
     */
    public static final List<String> NO_SECURITY_URLS = List.of(
            "/",
            "/oauth2/**",
            "/login/oauth2/code/**",
            "/favicon.ico",
            "/api/user/login",
            "/api/user/social/signup/**",
            "/api/user/signup",
            "/admin/**",
            "/WEB-INF/**",
            "/api/user/check/email/**",
            "/oauth2/authorization/**",
            "/login/oauth2/code/**"
    );

    /**
     * @implNote 필터 안거치는 uri 목록
     */
    public static final List<String> NO_FILTER_URLS = List.of(
            "/api/user/login",
            "/api/user/signup",
            "/oauth2/authorization",
            "/login/oauth2"
//            "/admin",
//            "/WEB-INF"
    );

}
