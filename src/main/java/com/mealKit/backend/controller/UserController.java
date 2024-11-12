package com.mealKit.backend.controller;



import com.mealKit.backend.dto.UserDetailDTO;
import com.mealKit.backend.dto.UserLoginDTO;
import com.mealKit.backend.dto.UserSignUpDTO;
import com.mealKit.backend.dto.UserSocialSignUpDTO;
import com.mealKit.backend.exception.ResponseDto;
import com.mealKit.backend.interceptor.Pid;
import com.mealKit.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    // 소셜 로그인
    @PatchMapping("/social/signup/{email}")
    public ResponseDto<String> socialSignup(@PathVariable String email, @RequestBody @Valid UserSocialSignUpDTO socialDto) {
        return ResponseDto.ok(String.valueOf(userService.socialSignUp(email, socialDto)));
    }

    // user delete
    @PatchMapping("/delete/{id}")
    public ResponseDto<String> deleteId(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseDto.ok("userService.delete : " + id);
    }

    // 비밀번호 변경
    @PatchMapping("/modify/password/{id}")
    public ResponseDto<String> modifyPassword(@PathVariable Integer id, @RequestBody String password) {
        userService.modifyPassword(id, password);
        return ResponseDto.ok("userService.modifyPassword : " + id);
    }

    // 핸드폰 변경
    @PatchMapping("/modify/phone/{id}")
    public ResponseDto<String> modifyPhone(@PathVariable Integer id, @RequestBody String phone) {
        userService.modifyPhone(id, phone);
        return ResponseDto.ok("userService.modifyPhone : " + id);
    }

    // 주소 변경
    @PatchMapping("/modify/address/{id}")
    public ResponseDto<String> modifyAddress(@PathVariable Integer id, @RequestBody Map<?,?> address) {
        userService.modifyAddress(id, address.get("zipcode").toString(), address.get("streetAdr").toString(), address.get("detailAdr").toString());
        return ResponseDto.ok("userService.modifyAddress : " + id);
    }

    @PatchMapping("/modify/admin/{id}")
    public ResponseDto<String> modifyRoleAdmin(@PathVariable Integer id) {
        userService.modifyRoleAdmin(id);
        return ResponseDto.ok("userService.modifyRoleAdmin : " + id);
    }

    @PatchMapping("/modify/user/{id}")
    public ResponseDto<String> modifyRoleUser(@PathVariable Integer id) {
        userService.modifyRoleUser(id);
        return ResponseDto.ok("userService.modifyRoleUser : " + id);
    }

    // 프로필 조회 목적
    @GetMapping("/info")
    public ResponseDto<UserDetailDTO> getUserDetail(@Pid String pid, HttpServletResponse response) {
        log.info(pid);
//        response.setHeader("content-type", "application/json");
        return ResponseDto.ok(userService.getUserByPid(
                pid));
    }

    // 회원가입: 이메일 확인 -> 이미 존재하는 이메일이면 거부
    @GetMapping("/check/email/{email}")
    public ResponseDto<Boolean> checkEmail(@PathVariable String email) {
        return ResponseDto.ok(!userService.existByEmail(email));
    }

    // 폼 회원가입
    @PostMapping("/signup")
    public ResponseDto<String> signUp(@RequestBody @Valid UserSignUpDTO userSignUpDTO) {
        userService.signUp(userSignUpDTO);
        return ResponseDto.ok("userService.signUp");
    }

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        //log.info("id : {}, pw : {}" , userLoginDTO.getEmail(), userLoginDTO.getPassword());
        return ResponseDto.ok(userService.login(
                userLoginDTO
        ));
    }

    @GetMapping("/logout")
    public ResponseDto<String> logout(@Pid String pid) {
        log.info(pid);
        return ResponseDto.ok(String.valueOf(userService.logout(pid)));
    }


    /*
    @GetMapping("/redis/check")
    public void checkRedis(HttpServletRequest request) {
        userService.checkRedisValue(request);
    }
     */

    /*
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok("logout success");
    }

     */


//    @PatchMapping("/{name}")
//    public void modifyUser(@RequestBody UserDTO userDTO) {
//        User user = userService.readByName(userDTO.getName());
//        userService.modifyName(user, userDTO.getNickname());
//    }

}
