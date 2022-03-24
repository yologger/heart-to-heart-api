package com.yologger.heart_to_heart_api.controller.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        List<String> profiles = Arrays.asList(env.getActiveProfiles());

        if (profiles.contains("dev1")) {
            return "dev1";
        } else if (profiles.contains("dev2")) {
            return "dev2";
        } else if (profiles.contains("prod1")) {
            return "prod1";
        } else if (profiles.contains("prod2")) {
            return "prod2";
        } else if (profiles.contains("local")) {
            return "local";
        } else {
            return "default";
        }
    }
}