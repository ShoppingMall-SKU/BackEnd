package com.mealKit.backend.security;

import java.util.List;

/**
 * @serial
 */
public class Constant {

    /**
     * @implNote 필터 안거치는 uri 목록
     */
    public static List<String> allowedUrls = List.of(
            "/",
            "/oauth2/**",
            "/auth/**",
            "/login/**",
            "/auth/google",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/api-docs",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/api/user/login/**",
            "/api/user/signup/**",
            "/api/product/list/**",
            "/api/product/detail/**",
            "/api/product/search/**"
    );

}
