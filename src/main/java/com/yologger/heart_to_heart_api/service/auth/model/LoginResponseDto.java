package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseDto {
    @JsonProperty("user_id") private Long userId;
    @JsonProperty("access_token") private String accessToken;
    @JsonProperty("refresh_token") private String refreshToken;
}
