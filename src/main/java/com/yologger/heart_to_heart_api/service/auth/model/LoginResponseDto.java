package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class LoginResponseDto {
    @JsonProperty("member_id") private Long memberId;
    @JsonProperty("access_token") private String accessToken;
    @JsonProperty("refresh_token") private String refreshToken;
    @JsonProperty("name") private String name;
    @JsonProperty("nickname") private String nickname;
}
