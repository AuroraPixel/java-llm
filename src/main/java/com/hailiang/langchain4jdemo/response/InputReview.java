package com.hailiang.langchain4jdemo.response;

import dev.langchain4j.service.UserMessage;

public interface InputReview {

    /**
     * 输入审查
     * @param text
     * @return
     */
    @UserMessage("帮我分析 {{it}} 这段话是否为积极健康且符合法律规范等")
    boolean inputReview(String text);
}
