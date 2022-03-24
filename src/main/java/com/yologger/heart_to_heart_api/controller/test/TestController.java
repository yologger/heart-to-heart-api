package com.yologger.heart_to_heart_api.controller.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test1")
    public String test1() {
        return "testtest";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }
}