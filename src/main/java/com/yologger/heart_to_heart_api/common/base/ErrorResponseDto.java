package com.yologger.heart_to_heart_api.common.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorResponseDto {
    private int status;
    private String message;
    private String code;
}

