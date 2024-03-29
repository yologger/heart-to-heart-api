package com.yologger.heart_to_heart_api.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yologger.heart_to_heart_api.config.SecurityConfig;
import com.yologger.heart_to_heart_api.controller.post.exception.NoPostExistException;
import com.yologger.heart_to_heart_api.repository.member.AuthorityType;
import com.yologger.heart_to_heart_api.repository.member.MemberEntity;
import com.yologger.heart_to_heart_api.service.post.PostService;
import com.yologger.heart_to_heart_api.service.post.model.DeletePostResponseDTO;
import com.yologger.heart_to_heart_api.service.post.model.GetPostsResponseDTO;
import com.yologger.heart_to_heart_api.service.post.model.PostDTO;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = PostController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("AuthController 테스트")
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService mockPostService;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("게시글 조회 테스트")
    class GetPostsTest {
        @Test
        @DisplayName("사용자 id로 게시글 조회")
        public void test_get_posts_by_id() throws Exception {
            // Given
            MemberEntity dummyMember = MemberEntity.builder()
                    .id(1L)
                    .email("cristiano@gmail.com")
                    .name("Cristiano Ronaldo")
                    .nickname("CR7")
                    .password("1234")
                    .authority(AuthorityType.USER)
                    .build();

            List<PostDTO> posts = new ArrayList<>(Arrays.asList(
                    PostDTO.builder()
                            .writerId(dummyMember.getId())
                            .writerEmail(dummyMember.getEmail())
                            .writerNickname(dummyMember.getNickname())
                            .avatarUrl(null)
                            .content("content1")
                            .build(),
                    PostDTO.builder()
                            .writerId(dummyMember.getId())
                            .writerEmail(dummyMember.getEmail())
                            .writerNickname(dummyMember.getNickname())
                            .avatarUrl(null)
                            .content("content2")
                            .build(), PostDTO.builder()
                            .writerId(dummyMember.getId())
                            .writerEmail(dummyMember.getEmail())
                            .writerNickname(dummyMember.getNickname())
                            .avatarUrl(null)
                            .content("content3")
                            .build()
            ));

            GetPostsResponseDTO dummyResponse = GetPostsResponseDTO.builder()
                    .size(posts.size())
                    .posts(posts)
                    .build();

            when(mockPostService.getAllPosts(anyLong(), anyInt(), anyInt())).thenReturn(dummyResponse);

            // When
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("member_id", "1");
            params.add("page", "0");
            params.add("size", "0");

            // Then
            mvc.perform(MockMvcRequestBuilders.get("/post/posts")
                            .params(params)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.posts").isArray())
                    .andExpect(jsonPath("$.posts").isNotEmpty())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("글 삭제 테스트")
    public class DeletePost {
        @Test
        @DisplayName("글 삭제 실패 테스트 - 게시글이 존재하지 않을 때")
        public void deletePost_failure_whenPostNotExist() throws Exception {
            Long notExistId = 1L;
            when(mockPostService.deletePost(anyLong())).thenThrow(NoPostExistException.class);

            mvc.perform(MockMvcRequestBuilders.delete("/post/delete/" + notExistId))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code", is("POST_004")))
                    .andDo(print());
        }

        @Test
        @DisplayName("글 삭제 성공 테스트")
        public void deletePost_success() throws Exception {
            Long dummyId = 1L;
            DeletePostResponseDTO dummyResponse = DeletePostResponseDTO.builder()
                    .message("deleted")
                    .build();
            when(mockPostService.deletePost(anyLong())).thenReturn(dummyResponse);

            mvc.perform(MockMvcRequestBuilders.delete("/post/delete/" + dummyId))
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$.message", is("deleted")))
                    .andDo(print());
        }
    }
}