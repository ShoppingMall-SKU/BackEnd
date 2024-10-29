package com.mealKit.backend.service;

import com.mealKit.backend.domain.enums.UserRole;
import com.mealKit.backend.dto.UserDetailDTO;
import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import com.mealKit.backend.jwt.JwtToken;
import com.mealKit.backend.redis.RedisConfig;
import com.mealKit.backend.domain.User;
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

    private final BCryptPasswordEncoder encoder;
    //private final RedisConfig redisConfig;

    // 회원가입(남은 내용 수정 기능)
    @Transactional
    public void socialSignUp(Integer userId, String password, String phone, String zipcode, String streetAdr, String detailAdr) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            user.get().setPassword(encoder.encode(password));
            user.get().setPhone(phone);
            user.get().setAddress(zipcode);
            user.get().setStreetAddress(streetAdr);
            user.get().setDetailAddress(detailAdr);
            user.get().setRole(UserRole.ROLE_USER);
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }
    // 유저 role 수정
    @Transactional
    public void modifyRoleUser(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            user.get().setRole(UserRole.ROLE_USER);
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }
    @Transactional
    public void modifyRoleAdmin(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            user.get().setRole(UserRole.ROLE_ADMIN);
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }
    // 비밀번호 수정
    @Transactional
    public void modifyPassword(Integer userId, String password) {
        Optional<User> user = userRepository.findById(userId);
        if (password.isBlank()){
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        if (user.isPresent()){
            user.get().setPassword(encoder.encode(password));
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }
    // 주소 수정
    @Transactional
    public void modifyAddress(Integer userId, String zipcode, String streetAdr, String detailAdr) {
        if (zipcode.isBlank()){
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        if (streetAdr.isBlank()){
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        if (detailAdr.isBlank()){
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            user.get().setAddress(zipcode);
            user.get().setStreetAddress(streetAdr);
            user.get().setDetailAddress(detailAdr);
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }
    // 핸드폰 수정
    @Transactional
    public void modifyPhone(Integer userId, String phone) {
        Optional<User> user = userRepository.findById(userId);
        if (phone.isBlank()){
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        if (user.isPresent()){
            user.get().setPhone(phone);
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }

    // 유저 삭제 -> useYN 컬럼으로 사용안함 처리
    public void delete (Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            user.get().setUseYN("N");
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }

    // User 프로필 조회 목적
    public UserDetailDTO getUserInfo(Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        return user.map(this::toUserDetailDTO)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
    }
    private UserDetailDTO toUserDetailDTO(User user) {
        return UserDetailDTO.builder()
                .name(user.getName())
                .password(user.getPassword())
                .pid(user.getPid())
                .phone(user.getPhone())
                .email(user.getEmail())
                .address(user.getAddress())
                .streetAdr(user.getStreetAddress())
                .detailAdr(user.getDetailAddress())
                .role(user.getRole())
                .pt(user.getProviderType())
                .build();
    }

    public void updateRefreshToken(User user, String refreshToken) {
//        user.setRefreshToken(refreshToken);
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

    public List<User> readByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    public boolean existByName(String name) {
        return userRepository.findByName(name).isPresent();
    }

    public boolean existByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    /*
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
    */
    /*
    public void logout(HttpServletRequest request) {
        String accessToken = jwt.extractAccessToken(request);
        if (!jwtService.validateToken(accessToken))
            throw new RuntimeException("invalid way");
        String email = jwtService.getEmailFromToken(accessToken);
        log.info(email);
        log.info(redisConfig.redisTemplate().opsForValue().get(email));
        redisConfig.redisTemplate().delete(email);
        log.info(redisConfig.redisTemplate().opsForValue().get(email));
    }
    */
    /*
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
     */
        /*
    public void checkRedisValue(HttpServletRequest request) {
        String accessToken = jwtService.extractAccessToken(request);
        log.info("redis : {}", redisConfig.redisTemplate().opsForValue().get(accessToken));
    }
    */

}
