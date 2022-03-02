package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequestDto {

    @NotBlank(message = "email must not be empty.")
    @Email(message = "email must be in email format.")
    @JsonProperty(value = "email")
    private String email;

    @NotBlank(message = "name must not be empty.")
    @JsonProperty(value = "name")
    private String name;

    @NotBlank(message = "'nickname must not be empty.")
    @JsonProperty(value = "nickname")
    private String nickname;

    @NotBlank(message = "password must not be empty.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "'password must contain at least one uppercase letter, lowercase letter, and special character, and can have a minimum of 8 characters and a maximum of 20 characters."
    )
    @JsonProperty(value = "password")
    private String password;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .email(this.email)
                .name(this.name)
                .nickname(this.nickname)
                .password(this.password)
                .build();
    }
}
