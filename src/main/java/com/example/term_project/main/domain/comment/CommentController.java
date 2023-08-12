package com.example.term_project.main.domain.comment;

import com.example.term_project.main.domain.comment.dto.SaveCommentRequestDto;
import com.example.term_project.main.domain.comment.dto.SaveCommentResponseDto;
import com.example.term_project.main.global.response.BaseResponse;
import com.example.term_project.main.global.response.ResponseException;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/comment")
public class CommentController {

    private final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    @PostMapping("/save")
    public BaseResponse<SaveCommentResponseDto> saveComment(@RequestBody SaveCommentRequestDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());
        LOGGER.info("userId : " + userId);
        try {
            SaveCommentResponseDto response = commentService.saveComment(request, userId);
            return new BaseResponse<>(response);
        } catch (ResponseException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }
}
