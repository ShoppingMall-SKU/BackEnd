package com.mealKit.backend.service;

import com.mealKit.backend.domain.enums.UserRole;
import com.mealKit.backend.dto.UserDetailDTO;
import com.mealKit.backend.dto.UserLoginDTO;
import com.mealKit.backend.dto.UserSignUpDTO;
import com.mealKit.backend.dto.UserSocialSignUpDTO;
import com.mealKit.backend.exception.CommonException;
import com.mealKit.backend.exception.ErrorCode;
import com.mealKit.backend.jwt.JwtToken;
import com.mealKit.backend.jwt.JwtUtil;
import com.mealKit.backend.redis.RedisConfig;
import com.mealKit.backend.domain.User;
import com.mealKit.backend.redis.RedisService;
import com.mealKit.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisConfig redisConfig;
    private final RedisService redisService;


    //private final RedisConfig redisConfig;

    public UserDetailDTO getUserByPid(String pid) {
        User user = userRepository.findByPid(pid).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        return UserDetailDTO.toEntity(user);
    }


    // 회원가입(남은 내용 수정 기능)
    @Transactional
    public Boolean socialSignUp(String email, UserSocialSignUpDTO socialDto) {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            user.get().setPhone(socialDto.getPhone());
            user.get().setAddress(socialDto.getZipcode());
            user.get().setStreetAddress(socialDto.getStreetAdr());
            user.get().setDetailAddress(socialDto.getDetailAdr());
            user.get().setRole(UserRole.ROLE_USER);

            return Boolean.TRUE;
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }
    // 유저 role 수정
    @Transactional
    public Boolean modifyRoleUser(String userPid) {
        Optional<User> user = userRepository.findByPid(userPid);
        if (user.isPresent()){
            user.get().setRole(UserRole.ROLE_USER);
            return Boolean.TRUE;
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }
    @Transactional
    public Boolean modifyRoleAdmin(String userPid) {
        Optional<User> user = userRepository.findByPid(userPid);
        if (user.isPresent()){
            user.get().setRole(UserRole.ROLE_ADMIN);
            return Boolean.TRUE;
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }
    // 비밀번호 수정 (일반 로그인만)
    @Transactional
    public Boolean modifyPassword(String userPid, String password) {
        Optional<User> user = userRepository.findByPid(userPid);
        if (password.isBlank()){
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        if (user.isPresent()){
            user.get().setPassword(encoder.encode(password));
            return Boolean.TRUE;
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }
    // 주소 수정
    @Transactional
    public Boolean modifyAddress(String userPid, String zipcode, String streetAdr, String detailAdr) {
        if (zipcode.isBlank()){
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        if (streetAdr.isBlank()){
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        if (detailAdr.isBlank()){
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        Optional<User> user = userRepository.findByPid(userPid);
        if (user.isPresent()){
            user.get().setAddress(zipcode);
            user.get().setStreetAddress(streetAdr);
            user.get().setDetailAddress(detailAdr);
            return Boolean.TRUE;
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }
    // 핸드폰 수정
    @Transactional
    public Boolean modifyPhone(String userPid, String phone) {
        Optional<User> user = userRepository.findByPid(userPid);
        if (phone.isBlank()){
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        if (user.isPresent()){
            user.get().setPhone(phone);
            return Boolean.TRUE;
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }

    // 유저 삭제 -> useYN 컬럼으로 사용안함 처리
    @Transactional
    public void delete (Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            user.get().setUseYN("N");
        }else{
            throw new CommonException(ErrorCode.NOT_FOUND_USER);
        }
    }


    // 폼 회원가입
    public void signUp (UserSignUpDTO userSignUpDTO) {

        userRepository.save(User.builder()
                        .name(userSignUpDTO.getName())
                        .email(userSignUpDTO.getEmail())
                        .password(encoder.encode(userSignUpDTO.getPassword()))
                        .phone(userSignUpDTO.getPhone())
                        .address(userSignUpDTO.getZipcode())
                        .streetAddress(userSignUpDTO.getStreetAdr())
                        .detailAddress(userSignUpDTO.getDetailAdr())
                        .role(UserRole.ROLE_USER)
                .build());
    }

    public String login(UserLoginDTO userLoginDTO) {
        String email = userLoginDTO.getEmail();
        String pw = userLoginDTO.getPassword();
        log.info("로그인 시도 : {}",email);
        User user = userRepository.findByEmail(email).orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_USER));

        List<SimpleGrantedAuthority> authorityList = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        log.info(authorityList.toString());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, pw, authorityList);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //log.info(SecurityContextHolder.getContext().getAuthentication().getName());
        String token = jwtUtil.createJwt(user.getPid(),"",authentication.getAuthorities().toString().substring(1,10), 1000 * 60 * 60L);
        //log.info(token);

        String refreshToken = jwtUtil.createRefreshToken();
        user.setRefreshToken(refreshToken);
        redisService.save(user.getPid(), refreshToken);

        return token;
    }

    public Boolean logout(String pid) {
        try {
            redisConfig.redisTemplate().delete(pid);
            return Boolean.TRUE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
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
    public void checkRedisValue(HttpServletRequest request) {
        String accessToken = jwtService.extractAccessToken(request);
        log.info("redis : {}", redisConfig.redisTemplate().opsForValue().get(accessToken));
    }
    */

}
