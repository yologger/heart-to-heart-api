package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ReissueTokenResponseDto {
    @JsonProperty("user_id") private Long userId;
    @JsonProperty("access_token") private String accessToken;
    @JsonProperty("refresh_token") private String refreshToken;
    @JsonProperty("name") private String name;
    @JsonProperty("nickname") private String nickname;
    @JsonProperty("email") private String email;
}
