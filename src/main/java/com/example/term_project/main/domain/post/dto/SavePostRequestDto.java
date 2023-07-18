package com.example.term_project.main.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavePostRequestDto {
    private String title;
    private String content;
    private List<String> urlList;
}
