package com.yologger.heart_to_heart_api.service.post.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Post {
    private Long id;
    private Long writerId;
    private String writerEmail;
    private String writerNickname;
    private String avatarUrl;
    private String content;
    private List<String> imageUris;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
