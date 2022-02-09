package com.yologger.heart_to_heart_api.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test1")
    public String test1() {
        return "test1";
    }

    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }

    @GetMapping("/test3")
    public String test3() {
        return "test3";
    }

    @GetMapping("/test4")
    public String test4() {
        return "test4";
    }

    @GetMapping("/test5")
    public String test5() {
        return "test5";
    }

    @GetMapping("/test6")
    public String test6() {
        return "test6";
    }

    @GetMapping("/test7")
    public String test7() {
        return "test7";
    }

    @GetMapping("/test8")
    public String test8() {
        return "test8";
    }
}