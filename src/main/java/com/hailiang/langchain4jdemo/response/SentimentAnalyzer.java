package com.hailiang.langchain4jdemo.response;

import com.hailiang.langchain4jdemo.response.enu.SentimentEnum;
import dev.langchain4j.service.UserMessage;

public interface SentimentAnalyzer {
    @UserMessage("分享一下 {{it}} 这段话的情感是什么?")
    SentimentEnum analyzeSentimentOf(String text);
}
