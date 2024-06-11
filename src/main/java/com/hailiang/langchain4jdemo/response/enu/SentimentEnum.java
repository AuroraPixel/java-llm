package com.hailiang.langchain4jdemo.response.enu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SentimentEnum {

    NEGATIVE("negative", "负面"),
    POSITIVE("positive", "正面"),
    NEUTRAL("neutral", "中性");

    private final String key;

    private final String value;
}
