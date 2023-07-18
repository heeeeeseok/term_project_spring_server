package com.example.term_project.main.domain.user;

import com.example.term_project.main.domain.user.dto.LoginResponseDto;
import com.example.term_project.main.domain.user.dto.SearchPostsResponseDto;
import com.example.term_project.main.global.response.BaseResponse;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import com.example.term_project.main.domain.user.dto.LoginRequestDto;
import com.example.term_project.main.domain.user.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    @PostMapping("/test")
    public BaseResponse<Long> test() throws ResponseException {
        return new BaseResponse<>(-1L);
    }

    @PostMapping("/signup")
    public BaseResponse<Long> signup(@RequestBody SignupRequestDto signupReq) throws ResponseException {
        try {
            Long id = userService.signup(signupReq);
            LOGGER.info("new UserId : " + id);
            return new BaseResponse<>(id);
        } catch (ResponseException e) {
            LOGGER.info(e.getMessage());
            return new BaseResponse<>(e.getErrorCode());
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return  new BaseResponse<>(ResponseCode.INTERNAL_ERROR);
        }
    }

    @PostMapping("/login")
    public BaseResponse<LoginResponseDto> login(@RequestBody LoginRequestDto loginReq) {
        try {
            LoginResponseDto response = userService.login(loginReq);
            return new BaseResponse<>(response);
        } catch (ResponseException e) {
            LOGGER.info(e.getMessage());
            return new BaseResponse<>(e.getErrorCode());
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return new BaseResponse<>(ResponseCode.INTERNAL_ERROR);
        }
    }

    @GetMapping("/posts")
    public BaseResponse<List<SearchPostsResponseDto>> searchPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        List<SearchPostsResponseDto> responseList = userService.searchPosts(userId);
        return new BaseResponse<>(responseList);
    }
}
