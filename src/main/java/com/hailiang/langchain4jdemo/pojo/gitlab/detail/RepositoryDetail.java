package com.hailiang.langchain4jdemo.pojo.gitlab.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 仓库信息详情
 */
@Data
public class RepositoryDetail {
    /**
     * 仓库名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 仓库URL
     */
    @JsonProperty("url")
    private String url;

    /**
     * 描述
     */
    @JsonProperty("description")
    private String description;

    /**
     * 主页
     */
    @JsonProperty("homepage")
    private String homepage;
}
