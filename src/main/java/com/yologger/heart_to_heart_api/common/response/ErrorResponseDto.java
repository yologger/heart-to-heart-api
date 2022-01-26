package com.yologger.heart_to_heart_api.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {
    private String code;
    private String message;
}

