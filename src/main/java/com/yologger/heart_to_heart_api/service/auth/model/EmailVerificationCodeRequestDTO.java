package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiModel(
        value = "이메일 인증코드 전송 모델",
        description = "email을 포함하는 이메일 인증코드 전송 모델"
)
public class EmailVerificationCodeRequestDTO {
    @NotBlank(message = "email must not be empty.")
    @Email(message = "email must be in email format.")
    @JsonProperty(value = "email")
    @ApiModelProperty(value = "이메일", example = "ronaldo@gmail.com")
    private String email;
}
