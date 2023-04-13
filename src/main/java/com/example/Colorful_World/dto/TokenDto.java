package com.example.Colorful_World.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {

    public String accessToken;
    public String refreshToken;
    public Long refreshTokenExpiration;


}
