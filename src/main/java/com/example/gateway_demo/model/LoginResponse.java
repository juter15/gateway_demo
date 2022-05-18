package com.example.gateway_demo.model;

import lombok.Data;

@Data

public class LoginResponse {
    private Long userSeq;
    private String userNa;
    private String userId;
    private String userPw;

    private String accessToken;
    private String refreshToken;
}
