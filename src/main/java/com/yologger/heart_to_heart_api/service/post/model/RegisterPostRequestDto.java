package com.yologger.heart_to_heart_api.service.post.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterPostRequestDto {
    private MultipartFile[] files;
    private Long memberId;
    private String content;
}
