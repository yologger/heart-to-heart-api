package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class VerifyAccessTokenResponse {
    @JsonProperty("message") private String message;
}
