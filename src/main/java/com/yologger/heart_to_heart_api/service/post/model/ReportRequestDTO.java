package com.yologger.heart_to_heart_api.service.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReportRequestDTO {

    @JsonProperty(value = "member_id")
    @NotNull(message = "'member_id' must not be null.")
    private Long memberId;

    @JsonProperty(value = "target_id")
    @NotNull(message = "'target_id' must not be null.")
    private Long targetId;
}
