package com.yologger.heart_to_heart_api.controller.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static com.google.common.truth.Truth.assertThat;

@DisplayName("ProfileController 테스트")
class ProfileControllerTest {

    @Test
    @DisplayName("현재 활성화된 Profile이 staging1일 때, staging1이 조회된다.")
    public void when_active_profile_is_alpha1() {
        // Given
        String expectedProfile = "staging1";

        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("security");
        env.addActiveProfile("db");

        ProfileController controller = new ProfileController(env);

        // When
        String activeProfile = controller.profile();

        // Then
        assertThat(activeProfile).isEqualTo(expectedProfile);
    }

    @Test
    @DisplayName("현재 활성화된 Profile이 없을 때, default가 조회된다.")
    public void when_no_active_profile() {
        // Given
        String expectedProfile = "default";

        MockEnvironment env = new MockEnvironment();

        ProfileController controller = new ProfileController(env);

        // When
        String activeProfile = controller.profile();

        // Then
        assertThat(activeProfile).isEqualTo(expectedProfile);
    }
}