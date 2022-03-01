package com.yologger.heart_to_heart_api.controller.test;

import com.yologger.heart_to_heart_api.service.test.TestService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(TestController.class)
@DisplayName("TestController 테스트")
class TestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TestService testService;

    @Test
    @DisplayName("@WebMvcTest 테스트")
    public void test1() throws Exception {
        mvc.perform(get("/test/test1"))
                .andExpect(content().string("test1"));
    }

    @Test
    @DisplayName("@MockBean 테스트")
    public void test2() throws Exception {
        given(testService.test()).willReturn("test");

        mvc.perform(get("/test/test2"))
                .andExpect(content().string("test"));
    }
}