package com.yologger.heart_to_heart_api.service.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class UploadAvatarResponseDTO {
    @JsonProperty("member_id") private Long memberId;
    @JsonProperty("avatar_url") private String avatarUrl;
}
