package com.yologger.heart_to_heart_api.service.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetBlockingMembersRequestDTO {
    @NotNull
    @JsonProperty(value = "member_id")
    private Long memberId;
}
