package com.mealKit.backend.service;

import com.mealKit.backend.jwt.JwtService;
import com.mealKit.backend.jwt.JwtToken;
import com.mealKit.backend.redis.RedisConfig;
import com.mealKit.backend.domain.User;
import com.mealKit.backend.domain.User.role;
import com.mealKit.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;
    private final RedisConfig redisConfig;

    public void signUp (String name, String password, String phone, String email, String address) {

        userRepository.save(User
                .builder()
                        .address(address)
                        .phone(phone)
                        .name(name)
                        .password(password)
                        .email(email)
                .build());
    }

//    public String loginBySocial(HttpServletRequest request, HttpServletResponse response, String email) {
//
//    }


    public String login(String email, String pw) {
        log.info("로그인 시도 : {}",email);
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "이메일 및 패스워드를 확인 하십시오.";
        }

        List<SimpleGrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(user.getRole().name()));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, pw, authorityList);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken token = jwtService.generateToken(authentication, user.getRole());


        Long now = new Date().getTime();
        Long expiration = jwtService.getExpirationFromToken(token.getRefreshToken()).getTime();
        redisConfig.redisTemplate().opsForValue().set(user.getEmail(), token.getRefreshToken(), expiration - now, TimeUnit.MILLISECONDS);
        updateRefreshToken(user, token.getRefreshToken()); // 이게 굳이 필요가 있나 어차피 redis 쓰는데

        return token.getAccessToken();

    }

    public void logout(HttpServletRequest request) {
        String accessToken = jwtService.extractAccessToken(request);
        if (!jwtService.validateToken(accessToken))
            throw new RuntimeException("invalid way");
        String email = jwtService.getEmailFromToken(accessToken);
        log.info(email);
        log.info(redisConfig.redisTemplate().opsForValue().get(email));
        redisConfig.redisTemplate().delete(email);
        log.info(redisConfig.redisTemplate().opsForValue().get(email));
    }



    public void checkRedisValue(HttpServletRequest request) {
        String accessToken = jwtService.extractAccessToken(request);
        log.info("redis : {}", redisConfig.redisTemplate().opsForValue().get(accessToken));
    }

    public void delete (User user) {
        userRepository.delete(user);
    }

    public void updateRefreshToken(User user, String refreshToken) {
//        user.setRefreshToken(refreshToken);
//        userRepository.save(user);
    }

    public void modifyPassword(User user, String pw) {
//        user.setPassword(encoder.encode(pw));
//        userRepository.save(user);
    }

    public void modifyName(User user, String name) {
//        user.setName(name);
//        userRepository.save(user);
    }

    public User readByName (String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new RuntimeException("data not found");
        }
    }


    public List<User> readAll() {
        return userRepository.findAll();
    }

    public List<User> readByRole(role role) {
        return userRepository.findByRole(role);
    }

    public boolean existByName(String name) {
        return userRepository.findByName(name).isPresent();
    }

    public boolean existByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
