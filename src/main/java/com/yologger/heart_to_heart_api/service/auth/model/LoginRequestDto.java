package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginRequestDto {

    @NotBlank(message = "email must not be empty.")
    @Email(message = "email must be in email format.")
    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "password")
    private String password;
}
