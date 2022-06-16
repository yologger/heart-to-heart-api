package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiModel(
        value = "토큰 갱신 모델",
        description = "member_id, refresh_token을 포함하는 로그인 요청 모델"
)
public class ReissueTokenRequestDTO {

    @JsonProperty(value = "member_id")
    @NotNull(message = "email must not be empty.")
    @ApiModelProperty(value = "사용자 ID")
    private Long memberId;

    @JsonProperty(value = "refresh_token")
    @NotBlank(message = "email must not be empty.")
    @ApiModelProperty(value = "Refresh token")
    private String refreshToken;
}
