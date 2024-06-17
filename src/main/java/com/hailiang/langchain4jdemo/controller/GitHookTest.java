package com.hailiang.langchain4jdemo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHookTest {

    @PostMapping("/test")
    public void Test(@RequestBody String test) {
        System.out.println("git hook test-1:"+test);
    }
}
