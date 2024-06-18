package com.hailiang.langchain4jdemo.pojo.gitlab.detail;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * GitLab提交信息详情
 */
@Data
public class CommitDetail {
    /**
     * 提交ID
     */
    private String id;

    /**
     * 短提交ID
     */
    @JsonProperty("short_id")
    private String shortId;

    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * 父提交ID列表
     */
    @JsonProperty("parent_ids")
    private List<String> parentIds;

    /**
     * 提交标题
     */
    private String title;

    /**
     * 提交消息
     */
    private String message;

    /**
     * 作者名称
     */
    @JsonProperty("author_name")
    private String authorName;

    /**
     * 作者邮箱
     */
    @JsonProperty("author_email")
    private String authorEmail;

    /**
     * 作者日期
     */
    @JsonProperty("authored_date")
    private String authoredDate;

    /**
     * 提交者名称
     */
    @JsonProperty("committer_name")
    private String committerName;

    /**
     * 提交者邮箱
     */
    @JsonProperty("committer_email")
    private String committerEmail;

    /**
     * 提交日期
     */
    @JsonProperty("committed_date")
    private String committedDate;

    /**
     * 附加信息
     */
    private Map<String, Object> trailers;

    /**
     * 提交详细信息的URL
     */
    @JsonProperty("web_url")
    private String webUrl;
}
