package com.example.term_project.main.domain.post;

import com.example.term_project.main.domain.post.dto.SavePostRequestDto;
import com.example.term_project.main.domain.user.UserRepository;
import com.example.term_project.main.domain.user.entity.UserEntity;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long savePost(SavePostRequestDto request, Long userId) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            PostEntity newPost = PostEntity.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .urlList(request.getUrlList())
                    .commentCount(0)
                    .recommendCount(0)
                    .user(user)
                    .build();

            return postRepository.save(newPost).getPostId();
        } else {
            throw new ResponseException(ResponseCode.BAD_REQUEST);
        }
    }
}
