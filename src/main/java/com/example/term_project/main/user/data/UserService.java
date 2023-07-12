package com.example.term_project.main.user.data;

import com.example.term_project.main.JwtTokenProvider;
import com.example.term_project.main.response.ResponseException;
import com.example.term_project.main.user.dto.LoginRequestDto;
import com.example.term_project.main.user.dto.LoginResponseDto;
import com.example.term_project.main.user.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Transactional
    public Long signup(SignupRequestDto signupReq) {
        UserEntity newUser = UserEntity.builder()
                .androidId(-1L)
                .email(signupReq.getEmail())
                .password(signupReq.getPassword())
                .userName(signupReq.getUserName())
                .build();

        return userRepository.save(newUser).getUserId();
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
                throw new ResponseException();
            }
        } else {
            throw new ResponseException();
        }
    }
}
