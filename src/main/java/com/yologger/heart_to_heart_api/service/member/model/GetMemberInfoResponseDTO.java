package com.yologger.heart_to_heart_api.service.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetMemberInfoResponseDTO {
    @JsonProperty("member_id") private Long memberId;
    @JsonProperty("email") private String email;
    @JsonProperty("name") private String name;
    @JsonProperty("nickname") private String nickname;
    @JsonProperty("avatar_url") private String avatarUrl;
    @JsonProperty("post_size") private Integer postSize;
    @JsonProperty("follower_size") private Integer followerSize;
    @JsonProperty("following_size") private Integer followingSize;
}
