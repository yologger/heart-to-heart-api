package com.yologger.heart_to_heart_api.service.member.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetBlockingMembersResponseDTO {
    @JsonProperty("size") private Integer size;
    @JsonProperty("members") private List<MemberDTO> memberDTOS;
}
