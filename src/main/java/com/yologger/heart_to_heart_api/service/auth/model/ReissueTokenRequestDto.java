package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReissueTokenRequestDto {

    @JsonProperty(value = "member_id")
    @NotNull(message = "email must not be empty.")
    private Long memberId;

    @JsonProperty(value = "refresh_token")
    @NotBlank(message = "email must not be empty.")
    private String refreshToken;
}
