package com.example.term_project.main.domain.post;

import com.example.term_project.main.domain.post.dto.EditPostRequestDto;
import com.example.term_project.main.domain.post.dto.PostDto;
import com.example.term_project.main.domain.post.dto.SavePostRequestDto;
import com.example.term_project.main.global.response.BaseResponse;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @GetMapping("/print")
    public BaseResponse<List<PostDto>> getPostList() {
        List<PostDto> postList = new ArrayList<>();
        postList = postService.getPostList();
        return new BaseResponse<>(postList);
    }

    @PostMapping("/save")
    public BaseResponse<Long> savePost(@RequestPart(value = "savePostReq") SavePostRequestDto request,
                                       @RequestParam(value = "images", required = false) List<MultipartFile> multipartFiles) throws ResponseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        try {
            Long postId = postService.savePost(request, multipartFiles, userId);
            return new BaseResponse<>(postId);
        } catch (ResponseException e) {
            return new BaseResponse<>(e.getErrorCode());
        } catch (Exception e) {
            LOGGER.info("internal error occurred in post save");
            return new BaseResponse<>(ResponseCode.INTERNAL_ERROR);
        }
    }

    @PatchMapping("/edit")
    public BaseResponse<Long> editPost(@RequestPart(value = "editPostReq") EditPostRequestDto request,
                                       @RequestPart(value = "images", required = false) MultipartFile multipartFile) throws ResponseException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = Long.parseLong(authentication.getName());

        try {
            Long postId = postService.editPost(request, multipartFile, request.getPostId());
            return new BaseResponse<>(postId);
        } catch (ResponseException e) {
            return new BaseResponse<>(e.getErrorCode());
        } catch (Exception e) {
            LOGGER.info("internal error occurred in edit post");
            return new BaseResponse<>(ResponseCode.INTERNAL_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public BaseResponse<Long> deletePost(Long postId) throws ResponseException {
        try {
            Long delPostId = postService.deletePost(postId);
            return new BaseResponse<>(delPostId);
        } catch (ResponseException e) {
            return new BaseResponse<>(e.getErrorCode());
        } catch (Exception e) {
            return new BaseResponse<>(ResponseCode.INTERNAL_ERROR);
        }
    }
}
