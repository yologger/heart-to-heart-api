package com.yologger.heart_to_heart_api.service.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAccountResponseDTO {
    @JsonProperty("message") private String message;
}
