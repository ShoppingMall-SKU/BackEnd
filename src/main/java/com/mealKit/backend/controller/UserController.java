package com.mealKit.backend.controller;



import com.mealKit.backend.dto.UserDetailDTO;
import com.mealKit.backend.dto.UserSignUpDTO;
import com.mealKit.backend.dto.UserSocialSignUpDTO;
import com.mealKit.backend.exception.ResponseDto;
import com.mealKit.backend.service.UserService;
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
    @PatchMapping("/social/signup/{id}")
    public ResponseDto<String> socialSignup(@PathVariable Integer id, @RequestBody @Valid UserSocialSignUpDTO socialDto) {
        userService.socialSignUp(id,socialDto);
        return ResponseDto.ok("userService.socialSignup : " + id);
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
    @GetMapping("/info/{id}")
    public ResponseDto<UserDetailDTO> getUserDetail(@PathVariable Integer id) {
        return ResponseDto.ok(userService.getUserInfo(id));
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
//    @PostMapping("/login")
//    public ResponseDto<String> login(HttpServletResponse response, @RequestBody UserLoginDTO userLoginDTO) throws UnsupportedEncodingException {
//        //log.info("id : {}, pw : {}" , userLoginDTO.getEmail(), userLoginDTO.getPassword());
//        String accessToken = userService.login(
//                userLoginDTO.getEmail(),
//                userLoginDTO.getPassword()
//        );
//        response.setHeader("Authorization", "Bearer " + accessToken);
//        return ResponseDto.ok("login success");
//    }


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
