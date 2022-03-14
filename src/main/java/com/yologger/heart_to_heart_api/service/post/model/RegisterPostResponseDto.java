package com.yologger.heart_to_heart_api.service.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPostResponseDto {
    @JsonProperty("writer_id") private Long writerId;
    @JsonProperty("post_id") private Long postId;
    @JsonProperty("content") private String content;
    @JsonProperty("imageUrls") private List<String> imageUrls;
}
