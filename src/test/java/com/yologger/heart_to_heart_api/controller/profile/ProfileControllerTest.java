package com.yologger.heart_to_heart_api.controller.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProfileController 테스트")
class ProfileControllerTest {

    @Test
    public void test() {
        // Given
        String expectedProfile = "dev";

        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);


        // When

        // Then
    }
}