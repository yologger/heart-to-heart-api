package com.yologger.heart_to_heart_api.service.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class SendEmailVerificationResponseDto {
    @JsonProperty("message") private String message;
}
