package com.yologger.heart_to_heart_api.service.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class UploadAvatarRequestDTO {
    @JsonProperty("member_id") private Long memberId;
    @JsonProperty("file") private MultipartFile file;
}
