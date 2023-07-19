package com.example.term_project.main.domain.post.comment;

import com.example.term_project.main.domain.post.PostEntity;
import com.example.term_project.main.domain.post.PostRepository;
import com.example.term_project.main.domain.post.comment.dto.SaveCommentRequestDto;
import com.example.term_project.main.domain.post.comment.dto.SaveCommentResponseDto;
import com.example.term_project.main.domain.user.UserRepository;
import com.example.term_project.main.domain.user.entity.UserEntity;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@RequiredArgsConstructor
public class CommentService {

    private final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public SaveCommentResponseDto saveComment(SaveCommentRequestDto request, Long userId) {
        LOGGER.info("postId : " + request.getPostId());
        Optional<PostEntity> postOptional = postRepository.findByPostId(request.getPostId());

        String nameInPost = "";
        if (postOptional.isPresent()) {
            PostEntity postEntity = postOptional.get();
            List<Long> commentedList = postEntity.getCommentedUserIdList();

            if (userId.equals(postEntity.getUser().getUserId())) {
                nameInPost = "작성자";
            }
            else if (request.getIsAnonymous() == 0) {
                Optional<UserEntity> userOptional = userRepository.findByUserId(userId);
                if (userOptional.isPresent()) {
                    UserEntity userEntity = userOptional.get();
                    nameInPost = userEntity.getUserName();
                } else {
                    throw new ResponseException(ResponseCode.BAD_REQUEST);
                }
            } else { // 익명일 때
                int idx = -1;

                // 이 게시물에 댓글을 처음 작성하는 사용자일 때
                if (!commentedList.contains(userId)) {
                    postEntity.getCommentedUserIdList().add(userId);
                }

                idx = commentedList.indexOf(userId);
                nameInPost = "익명" + (idx + 1);
            }

            CommentEntity newComment = CommentEntity.builder()
                    .content(request.getContent())
                    .nameInPost(nameInPost)
                    .build();
            commentRepository.save(newComment);

            return SaveCommentResponseDto.builder()
                    .content(request.getContent())
                    .name(nameInPost)
                    .build();
        } else {
            throw new ResponseException(ResponseCode.BAD_REQUEST);
        }
    }
}
