package com.hailiang.langchain4jdemo.pojo.gitlab.detail;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 合并参数详情
 */
@Data
public class MergeParamsDetail {
    /**
     * 强制删除源分支
     */
    @JsonProperty("force_remove_source_branch")
    private String forceRemoveSourceBranch;
}
