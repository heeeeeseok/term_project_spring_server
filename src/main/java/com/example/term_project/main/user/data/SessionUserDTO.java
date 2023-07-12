package com.example.term_project.main.user.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SessionUserDTO {
    private Long userId;
    private String userName;
    private String profileImageUrl;
}
