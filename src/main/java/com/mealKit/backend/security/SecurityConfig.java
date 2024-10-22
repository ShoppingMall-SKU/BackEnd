package com.mealKit.backend.security;


import com.mealKit.backend.jwt.JwtFilter;
import com.mealKit.backend.jwt.JwtService;
import com.mealKit.backend.oauth2.OAtuth2LoginFailureHandler;
import com.mealKit.backend.oauth2.OAuth2LoginSuccessHandler;
import com.mealKit.backend.repository.UserRepository;
import com.mealKit.backend.service.UserService;
import com.mealKit.backend.oauth2.OAuth2UserService;
import com.mealKit.backend.redis.RedisConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RedisConfig redisConfig;
    private final UserService userService;
    private final OAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**","/static/**").permitAll()
                                .requestMatchers("/api/product/**").permitAll()
                                //.requestMatchers("/api/product/**").permitAll()
                                .requestMatchers("/api/user/login").permitAll()
                                .requestMatchers("/api/user/login/social").permitAll()
                                .requestMatchers("/oauth2/code/google").permitAll()
                                .requestMatchers("/oauth2/callback").permitAll()
                                .anyRequest().authenticated()
                )


//                .oauth2Login( o -> o.defaultSuccessUrl("/oauth2/code/google")
//                        .userInfoEndpoint(userService -> userService.userService(oAuth2UserService)))


                .addFilterAfter(new JwtFilter(jwtService, redisConfig), UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(request -> corsConfigurationSource().getCorsConfiguration(request)));


        return http.build();
    }


    @Bean
    public static BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OAuth2LoginSuccessHandler loginSuccessHandler() {
        return new OAuth2LoginSuccessHandler(jwtService, userRepository, userService);
    }

    @Bean
    public OAtuth2LoginFailureHandler loginFailureHandler() {
        return new OAtuth2LoginFailureHandler();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization")); // 없어도 무방할거 같은데

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}



