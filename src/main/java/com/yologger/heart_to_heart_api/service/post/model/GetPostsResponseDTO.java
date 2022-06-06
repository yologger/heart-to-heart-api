package com.yologger.heart_to_heart_api.service.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class GetPostsResponseDTO {
    @JsonProperty("size") private Integer size;
    @JsonProperty("posts") private List<PostDTO> posts;

    @Builder
    public GetPostsResponseDTO(Integer size, List<PostDTO> posts) {
        this.size = size;
        this.posts = posts;
    }
}
