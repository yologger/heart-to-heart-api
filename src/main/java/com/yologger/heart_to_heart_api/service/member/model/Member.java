package com.yologger.heart_to_heart_api.service.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {
    @JsonProperty("id") private Long id;
    @JsonProperty("email") private String email;
    @JsonProperty("nickname") private String nickname;
    @JsonProperty("name") private String name;
    @JsonProperty("avatar_url") private String avatarUrl;
}
