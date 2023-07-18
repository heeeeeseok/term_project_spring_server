package com.example.term_project.main.domain.post;

import com.example.term_project.main.domain.post.dto.SavePostRequestDto;
import com.example.term_project.main.global.response.BaseResponse;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @PostMapping("/save")
    public BaseResponse<Long> savePost(@RequestBody SavePostRequestDto request) throws ResponseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        try {
            Long postId = postService.savePost(request, userId);
            return new BaseResponse<>(postId);
        } catch (ResponseException e) {
            return new BaseResponse<>(e.getErrorCode());
        } catch (Exception e) {
            LOGGER.info("internal error occurred in post save");
            return new BaseResponse<>(ResponseCode.INTERNAL_ERROR);
        }
    }
}
