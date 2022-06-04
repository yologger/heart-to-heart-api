package com.yologger.heart_to_heart_api.service.post.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RegisterPostRequestDTO {
    private MultipartFile[] files;
    private Long memberId;
    private String content;
}
