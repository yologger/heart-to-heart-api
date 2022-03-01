package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationCodeRequestDto {
    @NotBlank(message = "email must not be empty.")
    @Email(message = "email must be in email format.")
    @JsonProperty(value = "email")
    private String email;
}
