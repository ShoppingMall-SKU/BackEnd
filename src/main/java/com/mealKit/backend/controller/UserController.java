package com.mealKit.backend.controller;



import com.mealKit.backend.dto.request.UserLoginRequestDto;
import com.mealKit.backend.dto.response.UserDetailResponseDTO;
import com.mealKit.backend.dto.request.UserSignUpRequestDTO;
import com.mealKit.backend.dto.request.UserSocialSignUpRequestDTO;
import com.mealKit.backend.exception.ResponseDto;
import com.mealKit.backend.annotation.Pid;
import com.mealKit.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    // 소셜 로그인
    @PutMapping("/social/signup/{email}")
    public ResponseDto<String> socialSignup(@PathVariable String email, @RequestBody @Valid UserSocialSignUpRequestDTO socialDto) {
        return ResponseDto.ok(String.valueOf(userService.socialSignUp(email, socialDto)));
    }

    // user delete
    @PatchMapping("/delete/{id}")
    public ResponseDto<String> deleteId(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseDto.ok("userService.delete : " + id);
    }

    // 비밀번호 변경
    @PatchMapping("/modify/password")
    public ResponseDto<Boolean> modifyPassword(@Pid String pid, @RequestBody String password) {
        return ResponseDto.ok(userService.modifyPassword(pid, password));
    }

    // 핸드폰 변경
    @PatchMapping("/modify/phone")
    public ResponseDto<Boolean> modifyPhone(@Pid String pid, @RequestBody String phone) {
        return ResponseDto.ok(userService.modifyPhone(pid, phone));
    }

    // 주소 변경
    @PatchMapping("/modify/address")
    public ResponseDto<Boolean> modifyAddress(@Pid String pid, @RequestBody Map<String, String> address) {
        return ResponseDto.ok(userService.modifyAddress(pid, address.get("zipcode"), address.get("streetAdr"), address.get("detailAdr")));
    }

    @PatchMapping("/modify/admin")
    public ResponseDto<Boolean> modifyRoleAdmin(@Pid String pid) {
        return ResponseDto.ok(userService.modifyRoleAdmin(pid));
    }

    @PatchMapping("/modify/user")
    public ResponseDto<Boolean> modifyRoleUser(@Pid String pid) {
        return ResponseDto.ok(userService.modifyRoleUser(pid));
    }

    // 프로필 조회 목적
    @GetMapping("/info")
    public ResponseDto<UserDetailResponseDTO> getUserDetail(@Pid String pid, HttpServletResponse response) {
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
    public ResponseDto<String> signUp(@RequestBody @Valid UserSignUpRequestDTO userSignUpDTO) {
        userService.signUp(userSignUpDTO);
        return ResponseDto.ok("userService.signUp");
    }

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody UserLoginRequestDto userLoginDTO) {
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
