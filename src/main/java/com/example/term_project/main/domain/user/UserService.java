package com.example.term_project.main.domain.user;

import com.example.term_project.main.domain.user.UserRepository;
import com.example.term_project.main.domain.user.data.UserEntity;
import com.example.term_project.main.domain.user.dto.LoginResponseDto;
import com.example.term_project.main.global.jwt.JwtTokenProvider;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import com.example.term_project.main.domain.user.UserController;
import com.example.term_project.main.domain.user.dto.LoginRequestDto;
import com.example.term_project.main.domain.user.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public Long signup(SignupRequestDto signupReq) throws ResponseException {
        UserEntity newUser = UserEntity.builder()
                .androidId(-1L)
                .email(signupReq.getEmail())
                .password(signupReq.getPassword())
                .userName(signupReq.getUserName())
                .build();

        try {
            return userRepository.save(newUser).getUserId();
        } catch (Exception e) {
            LOGGER.info("signup error occurred");
            throw new ResponseException(ResponseCode.SIGNUP_WITH_DUPLICATED_EMAIL);
        }
    }

    public LoginResponseDto login(LoginRequestDto loginReq) throws ResponseException {

        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(loginReq.getEmail());

        if (optionalUserEntity.isPresent()) {
            UserEntity user = optionalUserEntity.get();
            if (loginReq.getPassword().equals(user.getPassword())) {
                return LoginResponseDto.builder()
                        .jwtInfo(jwtTokenProvider.generateToken(user.getUserId()))
                        .userId(user.getUserId())
                        .build();
            } else {
                LOGGER.info("login with wrong password");
                throw new ResponseException(ResponseCode.LOGIN_WITH_WRONG_PASSWORD);
            }
        } else {
            LOGGER.info("login with wrong email (no such email)");
            throw new ResponseException(ResponseCode.LOGIN_WITH_WRONG_EMAIL);
        }
    }
}
