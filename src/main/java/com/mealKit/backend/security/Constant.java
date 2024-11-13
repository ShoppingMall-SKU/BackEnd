package com.mealKit.backend.security;

import java.util.List;

/**
 * @serial
 */
public class Constant {

    public static List<String> NO_FILTER_URLS = List.of(
            "/",
            "/oauth2/*",
            "/auth/**",
            "/login/oauth2/code/**",
            "/auth/google",
            "/favicon.ico",
            "/api/product/list",
            "/api/user/login"
    );

    /**
     * @implNote 필터 안거치는 uri 목록
     */
    public static List<String> allowedUrls = List.of(
        "/login/oauth2/code/google", "/swagger-ui/**"
    );

}
