package com.example.term_project.main.domain.post.comment;

import com.example.term_project.main.domain.post.PostEntity;
import com.example.term_project.main.domain.post.PostRepository;
import com.example.term_project.main.domain.post.comment.dto.SaveCommentRequestDto;
import com.example.term_project.main.domain.post.comment.dto.SaveCommentResponseDto;
import com.example.term_project.main.domain.user.UserRepository;
import com.example.term_project.main.domain.user.entity.UserEntity;
import com.example.term_project.main.global.response.BaseResponse;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ConstructorResult;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public SaveCommentResponseDto saveComment(SaveCommentRequestDto request, Long postId, Long userId) {
        Optional<PostEntity> postOptional = postRepository.findByPostId(postId);

        if (postOptional.isPresent()) {
            PostEntity postEntity = postOptional.get();
            List<Long> commentedList = postEntity.getCommentedUserIdList();

            if (request.getIsAnonymous() == 0) {
                Optional<UserEntity> userOptional = userRepository.findByUserId(userId);
                if (userOptional.isPresent()) {
                    UserEntity userEntity = userOptional.get();

                    CommentEntity newComment = CommentEntity.builder()
                            .content(request.getContent())
                            .nameInPost(userEntity.getUserName())
                            .build();
                    commentRepository.save(newComment);

                    return SaveCommentResponseDto.builder()
                            .content(request.getContent())
                            .name(userEntity.getUserName())
                            .build();
                } else {
                    throw new ResponseException(ResponseCode.BAD_REQUEST);
                }
            } else { // 익명일 때
                int idx = -1;
                if (commentedList.contains(userId)) {
                    idx = commentedList.indexOf(userId);
                } else {
                    postEntity.getCommentedUserIdList().add(userId);
                    idx = commentedList.indexOf(userId);
                }

                String name = "익명" + (idx + 1);
                CommentEntity newComment = CommentEntity.builder()
                        .content(request.getContent())
                        .nameInPost(name)
                        .build();
                commentRepository.save(newComment);

                return SaveCommentResponseDto.builder()
                        .content(request.getContent())
                        .name(name)
                        .build();
            }
        } else {
            throw new ResponseException(ResponseCode.BAD_REQUEST);
        }
    }
}
