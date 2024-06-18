package com.hailiang.langchain4jdemo.pojo.gitlab.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 最后提交详情
 */
@Data
public class LastCommitDetail {
    /**
     * 最后提交ID
     */
    @JsonProperty("id")
    private String id;

    /**
     * 最后提交消息
     */
    @JsonProperty("message")
    private String message;

    /**
     * 最后提交标题
     */
    @JsonProperty("title")
    private String title;

    /**
     * 时间戳
     */
    @JsonProperty("timestamp")
    private String timestamp;

    /**
     * 提交URL
     */
    @JsonProperty("url")
    private String url;

    /**
     * 提交作者
     */
    @JsonProperty("author")
    private GitUserDetail author;
}
