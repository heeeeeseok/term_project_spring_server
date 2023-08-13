package com.example.term_project.main.domain.post;

import com.example.term_project.main.domain.post.dto.EditPostRequestDto;
import com.example.term_project.main.domain.post.dto.PostDto;
import com.example.term_project.main.domain.post.dto.SavePostRequestDto;
import com.example.term_project.main.domain.user.UserRepository;
import com.example.term_project.main.domain.user.entity.UserEntity;
import com.example.term_project.main.global.response.ResponseCode;
import com.example.term_project.main.global.response.ResponseException;
import com.example.term_project.main.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    public List<PostDto> getPostList() {
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();

        for (PostEntity entity : postEntityList) {
            PostDto newPostDto = PostDto.builder()
                    .postId(entity.getPostId())
                    .editorName(entity.getEditorName())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .profileImageUrl(entity.getUser().getProfileImageUrl())
                    .commentCount(entity.getCommentEntityList().size())
                    .recommendCount(entity.getRecommendedUserIdList().size())
                    .urlList(entity.getUrlList())
                    .build();

            postDtoList.add(newPostDto);
        }

        return postDtoList;
    }

    @Transactional
    public Long savePost(SavePostRequestDto request, List<MultipartFile> multipartFiles, Long userId) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);
        List<String> urlList = new ArrayList<>();

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            String editorName = user.getUserName();
            if (request.getIsAnonymous() == 1) {
                editorName = "익명";
            }

            if (multipartFiles != null) {
                try {
                    for (MultipartFile file : multipartFiles) {
                        urlList.add(s3Service.uploadFile(file));
                    }
                } catch (IOException e) {
                    throw new ResponseException(ResponseCode.BAD_REQUEST);
                }
            }

            PostEntity newPost = PostEntity.builder()
                    .title(request.getTitle())
                    .content(request.getContent())
                    .editorName(editorName)
                    .urlList(urlList)
                    .isAnonymous(request.getIsAnonymous())
                    .recommendedUserIdList(new ArrayList<>())
                    .commentEntityList(new ArrayList<>())
                    .user(user)
                    .build();

            return postRepository.save(newPost).getPostId();
        } else {
            throw new ResponseException(ResponseCode.BAD_REQUEST);
        }
    }

    @Transactional
    public Long editPost(EditPostRequestDto request, List<MultipartFile> multipartFiles, Long postId) {
        Optional<PostEntity> postOptional = postRepository.findByPostId(postId);

        if (postOptional.isPresent()) {
            PostEntity editedPost = postOptional.get();

            editedPost.setContent(request.getContent());
            editedPost.setTitle(request.getContent());

            List<String> newUrlList = new ArrayList<>();
            try {
                for (MultipartFile file : multipartFiles) {
                    newUrlList.add(s3Service.uploadFile(file));
                }

                editedPost.setUrlList(newUrlList);
            } catch (IOException e) {
                throw new ResponseException(ResponseCode.S3_UPLOAD_FAILED);
            }

            if (editedPost.getUrlList().size() > 0) {
                for (String url : editedPost.getUrlList()) {
                    s3Service.deleteFile(url);
                }
            }

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
            if (delPost.getUrlList().size() > 0) {
                for (String url : delPost.getUrlList()) {
                    s3Service.deleteFile(url);
                }
            }
            Long delPostId = delPost.getPostId();
            postRepository.deleteById(postId);

            return delPostId;
        } else {
            throw  new ResponseException(ResponseCode.BAD_REQUEST);
        }
    }
}
