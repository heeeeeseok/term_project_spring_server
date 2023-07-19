package com.example.term_project.main.domain.post;

import com.example.term_project.main.domain.post.dto.EditPostRequestDto;
import com.example.term_project.main.domain.post.dto.PostDto;
import com.example.term_project.main.domain.post.dto.SavePostRequestDto;
import com.example.term_project.main.domain.user.UserRepository;
import com.example.term_project.main.domain.user.entity.UserEntity;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostDto> getPostList() {
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();

        for (PostEntity entity : postEntityList) {
            PostDto newPostDto = PostDto.builder()
                    .editorName(entity.getEditorName())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .build();

            postDtoList.add(newPostDto);
        }

        return postDtoList;
    }

    @Transactional
    public Long savePost(SavePostRequestDto request, MultipartFile multipartFile, Long userId) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            String editorName = user.getUserName();
            if (request.getIsAnonymous() == 1) {
                editorName = "익명";
            }

            PostEntity newPost = PostEntity.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .editorName(editorName)
                    .isAnonymous(request.getIsAnonymous())
                    .commentCount(0)
                    .recommendCount(0)
                    .commentEntitiyList(new ArrayList<>())
                    .user(user)
                    .build();

            return postRepository.save(newPost).getPostId();
        } else {
            throw new ResponseException(ResponseCode.BAD_REQUEST);
        }
    }

    @Transactional
    public Long editPost(EditPostRequestDto request, MultipartFile multipartFile, Long postId) {
        Optional<PostEntity> postOptional = postRepository.findByPostId(postId);

        if (postOptional.isPresent()) {
            PostEntity editedPost = postOptional.get();

            editedPost.setContent(request.getContent());
            editedPost.setTitle(request.getContent());
            // TODO : url update

            return postRepository.save(editedPost).getPostId();
        } else {
            throw  new ResponseException(ResponseCode.BAD_REQUEST);
        }
    }

    @Transactional
    public Long deletePost(Long postId) {
        Optional<PostEntity> postOptional = postRepository.findByPostId(postId);

        if (postOptional.isPresent()) {
            PostEntity delPost = postOptional.get();
            Long delPostId = delPost.getPostId();
            postRepository.deleteById(postId);

            return delPostId;
        } else {
            throw  new ResponseException(ResponseCode.BAD_REQUEST);
        }
    }
}
