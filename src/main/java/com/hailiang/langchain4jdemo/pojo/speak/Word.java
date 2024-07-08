package com.hailiang.langchain4jdemo.pojo.speak;

import lombok.Data;

@Data
public class Word {
    // 单词ID
    private Long wordId;
    // 单词开始时间
    private Long start;
    // 单词结束时间
    private Long end;
    // 单词文本
    private String text;
}
