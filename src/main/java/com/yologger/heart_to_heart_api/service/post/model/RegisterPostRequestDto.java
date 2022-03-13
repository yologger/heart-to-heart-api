package com.yologger.heart_to_heart_api.service.post.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPostRequestDto {
    private Long memberId;
    private String content;
}
