package com.hailiang.langchain4jdemo.response;

import dev.langchain4j.service.UserMessage;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public interface NumberAndDateExtractor {
    @UserMessage("从 {{it}} 这段话里获取数字")
    int extractInt(String text);

    @UserMessage("从 {{it}} 这段话里获取日期")
    LocalDateTime extractLong(String text);
}
