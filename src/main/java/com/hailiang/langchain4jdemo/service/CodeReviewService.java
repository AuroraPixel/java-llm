package com.hailiang.langchain4jdemo.service;

import com.hailiang.langchain4jdemo.pojo.gitlab.WebHookRequest;

public interface CodeReviewService {

    /**
     * 进行代码审查
     */
    void review(WebHookRequest request);
}
