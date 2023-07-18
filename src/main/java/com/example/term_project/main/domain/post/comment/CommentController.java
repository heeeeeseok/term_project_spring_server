package com.example.term_project.main.domain.post.comment;

import com.example.term_project.main.domain.post.comment.dto.SaveCommentRequestDto;
import com.example.term_project.main.domain.post.comment.dto.SaveCommentResponseDto;
import com.example.term_project.main.global.response.BaseResponse;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/save")
    public BaseResponse<SaveCommentResponseDto> saveComment(SaveCommentRequestDto request, Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        try {
            SaveCommentResponseDto response = commentService.saveComment(request, postId, userId);
            return new BaseResponse<>(response);
        } catch (ResponseException e) {
            return new BaseResponse<>(e.getErrorCode());
        }
    }
}
