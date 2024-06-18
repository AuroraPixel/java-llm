package com.hailiang.langchain4jdemo.pojo.gitlab.detail;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 引用详情
 */
@Data
public class ReferencesDetail {
    /**
     * 短引用
     */
    @JsonProperty("short")
    private String shortRef;

    /**
     * 相对引用
     */
    @JsonProperty("relative")
    private String relativeRef;

    /**
     * 完整引用
     */
    @JsonProperty("full")
    private String fullRef;
}
