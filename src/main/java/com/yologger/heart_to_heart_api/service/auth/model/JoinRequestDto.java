package com.yologger.heart_to_heart_api.service.auth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(
        value = "회원가입요청 모델",
        description = "email, name, nickname, password를 포함하는 회원가입요청 모델"
)
public class JoinRequestDto {

    @NotBlank(message = "email must not be empty.")
    @Email(message = "email must be in email format.")
    @JsonProperty(value = "email")
    @ApiModelProperty(value = "이메일", example = "ronaldo@gmail.com")
    private String email;

    @NotBlank(message = "name must not be empty.")
    @JsonProperty(value = "name")
    @ApiModelProperty(value = "이름", example = "Cristiano Ronaldo")
    private String name;

    @NotBlank(message = "'nickname must not be empty.")
    @JsonProperty(value = "nickname")
    @ApiModelProperty(value = "닉네임", example = "CR7")
    private String nickname;

    @NotBlank(message = "password must not be empty.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
            message = "'password must contain at least one uppercase letter, lowercase letter, and special character, and can have a minimum of 8 characters and a maximum of 20 characters."
    )
    @JsonProperty(value = "password")
    @ApiModelProperty(value = "패스워드", example = "4321Fdsa12!", notes = "'password must contain at least one uppercase letter, lowercase letter, and special character, and can have a minimum of 8 characters and a maximum of 20 characters.")
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
