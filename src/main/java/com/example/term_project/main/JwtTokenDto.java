package com.example.term_project.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenDto {
    private String grantType; // JWT에 대한 인증타입, Bearer
    private String accessToken;
    private String refreshToken;
    private Date expirationDate;
}
