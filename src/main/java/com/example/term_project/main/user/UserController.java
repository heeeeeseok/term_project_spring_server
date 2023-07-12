package com.example.term_project.main.user;

import com.example.term_project.main.response.BaseResponse;
import com.example.term_project.main.response.DataResponse;
import com.example.term_project.main.user.data.UserService;
import com.example.term_project.main.user.dto.LoginRequestDto;
import com.example.term_project.main.user.dto.LoginResponseDto;
import com.example.term_project.main.user.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/signup")
    public BaseResponse signup(@RequestBody SignupRequestDto signupReq) {
        Long id = userService.signup(signupReq);
        LOGGER.info("new UserId : " + id);
        return new BaseResponse();
    }

    @PostMapping("/login")
    public DataResponse<LoginResponseDto> login(@RequestBody LoginRequestDto loginReq) {
        LoginResponseDto response = userService.login(loginReq);
        return new DataResponse<>(response);
    }
}
