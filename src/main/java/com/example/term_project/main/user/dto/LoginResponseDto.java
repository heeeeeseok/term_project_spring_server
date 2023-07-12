package com.example.term_project.main.user.dto;

import com.example.term_project.main.JwtTokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private JwtTokenDto jwtInfo;
    private Long userId;
}
