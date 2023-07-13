package com.example.term_project.main.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Long userId;
    private String userName;
    private String profileImage;
}
