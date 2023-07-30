package com.example.term_project.main.domain.user.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchPostsResponseDto {
    private String title;
    private String content;
    private int recommendCount;
    private int commentCount;
}
