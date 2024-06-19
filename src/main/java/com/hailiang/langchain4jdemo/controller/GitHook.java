package com.hailiang.langchain4jdemo.controller;

import com.hailiang.langchain4jdemo.pojo.gitlab.WebHookRequest;
import com.hailiang.langchain4jdemo.service.CodeReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GitHook {
    @Autowired
    private CodeReviewService codeReviewService;

    @PostMapping("/test")
    public void Test(@RequestBody WebHookRequest request) {
        log.info("request:{}",request.toString());
        codeReviewService.review(request);
    }
}
