package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ConfirmVerificationCodeResponseDto {
    @JsonProperty("message")
    private String message;
}
