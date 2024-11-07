//package com.mealKit.backend.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.List;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowCredentials(true);
//        //configuration.setAllowedOrigins(List.of("http://localhost:3000","https://www.mealshop.shop"));
//        configuration.setAllowedOrigins(List.of("http://localhost:3000","https://www.mealshop.shop"));
//        configuration.addAllowedHeader("*");
//        configuration.addExposedHeader("Set-Cookie");
//        configuration.addAllowedMethod("*");
//
//        source.registerCorsConfiguration("/**", configuration);
//        return new CorsFilter(source);
//    }
//
//}