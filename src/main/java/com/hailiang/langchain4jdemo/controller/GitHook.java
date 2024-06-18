package com.hailiang.langchain4jdemo.controller;

import com.hailiang.langchain4jdemo.pojo.gitlab.WebHookRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GitHook {

    @PostMapping("/test")
    public void Test(@RequestBody WebHookRequest request) {
        System.out.println("git hook test-2:"+request);
    }
}
