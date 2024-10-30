package com.mealKit.backend.security;

import java.util.List;

public class Constant {

    public static String[] allowedUrls = {
            "/oauth2/**",
            "/auth/**",
            "/login/**",
            "/auth/google",
            "/api-ui.html",
            "/swagger-ui/index.html",
            "/api/**",
            "/api-docs",
            "/v3/api-docs/**",
            "/api-docs/**"
    };

}
