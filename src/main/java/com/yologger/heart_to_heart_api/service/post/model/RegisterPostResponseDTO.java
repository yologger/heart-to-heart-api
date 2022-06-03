package com.yologger.heart_to_heart_api.service.post.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPostResponseDTO {
    @JsonProperty("post_id") private Long postId;
    @JsonProperty("writer_id") private Long writerId;
    @JsonProperty("writer_email") private String writerEmail;
    @JsonProperty("writer_nickname") private String writerNickname;
    @JsonProperty("avatar_url") private String avatarUrl;
    @JsonProperty("content") private String content;
    @JsonProperty("image_urls") private List<String> imageUrls;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private LocalDateTime updatedAt;
}
