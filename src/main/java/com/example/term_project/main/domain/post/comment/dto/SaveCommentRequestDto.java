package com.example.term_project.main.domain.post.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveCommentRequestDto {
    private Long postId;
    private String content;
    private int isAnonymous;
}
