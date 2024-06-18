package com.hailiang.langchain4jdemo.pojo.gitlab.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * gitlab差异引用
 */
@Data
public class DiffRefsDetail {
    /**
     * 基础SHA
     */
    @JsonProperty("base_sha")
    private String baseSha;

    /**
     * 头部SHA
     */
    @JsonProperty("head_sha")
    private String headSha;

    /**
     * 起始SHA
     */
    @JsonProperty("start_sha")
    private String startSha;
}
