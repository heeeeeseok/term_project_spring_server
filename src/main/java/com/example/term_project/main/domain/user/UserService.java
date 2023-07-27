package com.example.term_project.main.domain.user;

import com.example.term_project.main.domain.post.PostEntity;
import com.example.term_project.main.domain.user.dto.SearchPostsResponseDto;
import com.example.term_project.main.domain.user.entity.UserEntity;
import com.example.term_project.main.domain.user.dto.LoginResponseDto;
import com.example.term_project.main.global.jwt.JwtTokenProvider;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import com.example.term_project.main.domain.user.dto.LoginRequestDto;
import com.example.term_project.main.domain.user.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(SignupRequestDto signupReq) throws ResponseException {
        UserEntity newUser = UserEntity.builder()
                .androidId(-1L)
                .email(signupReq.getEmail())
                .password(passwordEncoder.encode(signupReq.getPassword()))
                .userName(signupReq.getUserName())
                .build();

        Optional<UserEntity> checkDup = userRepository.findByEmail(newUser.getEmail());
        if (checkDup.isPresent())
            throw new ResponseException(ResponseCode.SIGNUP_WITH_DUPLICATED_EMAIL);

        checkDup = userRepository.findByUserName(newUser.getUserName());
        if (checkDup.isPresent())
            throw new ResponseException(ResponseCode.SIGNUP_WITH_DUPLICATED_USERNAME);

        try {
            return userRepository.save(newUser).getUserId();
        } catch (Exception e) {
            LOGGER.info("signup error occurred");
            throw new ResponseException(ResponseCode.INTERNAL_ERROR);
        }
    }

    public LoginResponseDto login(LoginRequestDto loginReq) throws ResponseException {

        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(loginReq.getEmail());

        if (optionalUserEntity.isPresent()) {
            UserEntity user = optionalUserEntity.get();
            if (loginReq.getPassword().equals(passwordEncoder.encode(user.getPassword()))) {
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

    public List<SearchPostsResponseDto> searchPosts(Long userId) throws ResponseException {
        List<SearchPostsResponseDto> responseList = new ArrayList<>();
        Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            List<PostEntity> postList = user.getPostList();

            for (PostEntity post : postList) {
                SearchPostsResponseDto response = SearchPostsResponseDto.builder()
                        .title(post.getTitle())
                        .content(post.getContent())
                        .recommendCount(post.getRecommendCount())
                        .commentCount(post.getCommentCount())
                        .build();

                responseList.add(response);
            }

            return responseList;
        } else {
            throw new ResponseException(ResponseCode.NOT_FOUND);
        }
    }
}
