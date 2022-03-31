package com.yologger.heart_to_heart_api.service.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnblockMemberRequestDTO {

    @NotNull
    @JsonProperty(value = "member_id")
    private Long memberId;

    @NotNull
    @JsonProperty(value = "target_id")
    private Long targetId;
}
