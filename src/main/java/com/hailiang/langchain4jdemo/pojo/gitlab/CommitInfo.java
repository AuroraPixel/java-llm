package com.hailiang.langchain4jdemo.pojo.gitlab;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.CommitDetail;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.DiffDetail;
import lombok.Data;

import java.util.List;

/**
 * GitLab提交信息
 */
@Data
public class CommitInfo {
    /**
     * 提交信息详情
     */
    private CommitDetail commit;

    /**
     * 所有提交记录详情
     */
    private List<CommitDetail> commits;

    /**
     * 差异详情
     */
    private List<DiffDetail> diffs;

    /**
     * 比较超时标志
     */
    @JsonProperty("compare_timeout")
    private Boolean compareTimeout;

    /**
     * 比较相同引用标志
     */
    @JsonProperty("compare_same_ref")
    private Boolean compareSameRef;

    /**
     * 比较详细信息的URL
     */
    @JsonProperty("compare_web_url")
    private String compareWebUrl;

    /**
     * 提交信息的URL
     */
    @JsonProperty("web_url")
    private String webUrl;
}
