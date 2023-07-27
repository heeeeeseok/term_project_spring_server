package com.example.term_project.main.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

import java.util.List;

@Getter
@SuperBuilder
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long postId;
    private String editorName;
    private String profileImageUrl;
    private String title;
    private String content;
    private int recommendCount;
    private int commentCount;
    private List<String> urlList;
}
