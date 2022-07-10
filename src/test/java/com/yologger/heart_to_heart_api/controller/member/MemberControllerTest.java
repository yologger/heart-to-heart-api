package com.yologger.heart_to_heart_api.controller.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yologger.heart_to_heart_api.config.SecurityConfig;
import com.yologger.heart_to_heart_api.controller.member.exception.InvalidMemberIdException;
import com.yologger.heart_to_heart_api.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = MemberController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("MemberController 테스트")
class MemberControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService mockMemberService;

    @Nested
    @DisplayName("사용자 차단 테스트")
    class BlockMemberTest {
        @Test
        @DisplayName("사용자 차단 실패 테스트")
        public void blockMember_failure() throws Exception {
            when(mockMemberService.block(any()))
                    .thenThrow(new InvalidMemberIdException("Invalid 'member_id'"));

            Long memberId = 1L;
            Long targetId = 2L;

            Map<String, String> body = new HashMap<>();
            body.put("member_id", memberId.toString());
            body.put("target_id", targetId.toString());

            mvc.perform(MockMvcRequestBuilders.post("/member/block")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(body))
            )
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("사용자 차단 해제 테스트")
    class UnblockMemberTest {
        @Test
        @DisplayName("사용자 차단 해제 실패 테스트")
        public void unblockMember_success() throws Exception {
            when(mockMemberService.unblock(any()))
                    .thenThrow(new InvalidMemberIdException("Invalid 'member_id'"));

            Long memberId = 1L;
            Long targetId = 2L;

            Map<String, String> body = new HashMap<>();
            body.put("member_id", memberId.toString());
            body.put("target_id", targetId.toString());

            mvc.perform(MockMvcRequestBuilders.post("/member/unblock")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(body))
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }
}