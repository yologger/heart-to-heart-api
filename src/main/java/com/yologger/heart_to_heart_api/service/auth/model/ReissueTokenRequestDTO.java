package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReissueTokenRequestDTO {

    @JsonProperty(value = "member_id")
    @NotNull(message = "email must not be empty.")
    private Long memberId;

    @JsonProperty(value = "refresh_token")
    @NotBlank(message = "email must not be empty.")
    private String refreshToken;
}
