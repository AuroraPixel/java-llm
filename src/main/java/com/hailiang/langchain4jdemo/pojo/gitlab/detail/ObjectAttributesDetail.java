package com.hailiang.langchain4jdemo.pojo.gitlab.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 对象属性
 */
@Data
public class ObjectAttributesDetail {
    /**
     * 分配人ID
     */
    @JsonProperty("assignee_id")
    private Integer assigneeId;

    /**
     * 作者ID
     */
    @JsonProperty("author_id")
    private Integer authorId;

    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * 描述
     */
    @JsonProperty("description")
    private String description;

    /**
     * 是否为草稿
     */
    @JsonProperty("draft")
    private Boolean draft;

    /**
     * 头部流水线ID
     */
    @JsonProperty("head_pipeline_id")
    private Integer headPipelineId;

    /**
     * ID
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * 内部ID
     */
    @JsonProperty("iid")
    private Integer iid;

    /**
     * 最后编辑时间
     */
    @JsonProperty("last_edited_at")
    private String lastEditedAt;

    /**
     * 最后编辑人ID
     */
    @JsonProperty("last_edited_by_id")
    private String lastEditedById;

    /**
     * 合并提交SHA
     */
    @JsonProperty("merge_commit_sha")
    private String mergeCommitSha;

    /**
     * 合并错误
     */
    @JsonProperty("merge_error")
    private String mergeError;

    /**
     * 合并参数
     */
    @JsonProperty("merge_params")
    private MergeParamsDetail mergeParams;

    /**
     * 合并状态
     */
    @JsonProperty("merge_status")
    private String mergeStatus;

    /**
     * 合并用户ID
     */
    @JsonProperty("merge_user_id")
    private String mergeUserId;

    /**
     * 当流水线成功时合并
     */
    @JsonProperty("merge_when_pipeline_succeeds")
    private boolean mergeWhenPipelineSucceeds;

    /**
     * 里程碑ID
     */
    @JsonProperty("milestone_id")
    private String milestoneId;

    /**
     * 源分支
     */
    @JsonProperty("source_branch")
    private String sourceBranch;

    /**
     * 源项目ID
     */
    @JsonProperty("source_project_id")
    private Integer sourceProjectId;

    /**
     * 状态ID
     */
    @JsonProperty("state_id")
    private Integer stateId;

    /**
     * 目标分支
     */
    @JsonProperty("target_branch")
    private String targetBranch;

    /**
     * 目标项目ID
     */
    @JsonProperty("target_project_id")
    private Integer targetProjectId;

    /**
     * 时间估算
     */
    @JsonProperty("time_estimate")
    private Integer timeEstimate;

    /**
     * 标题
     */
    @JsonProperty("title")
    private String title;

    /**
     * 更新时间
     */
    @JsonProperty("updated_at")
    private String updatedAt;

    /**
     * 更新者ID
     */
    @JsonProperty("updated_by_id")
    private String updatedById;

    /**
     * URL
     */
    @JsonProperty("url")
    private String url;

    /**
     * 源信息
     */
    @JsonProperty("source")
    private ProjectDetail source;

    /**
     * 目标信息
     */
    @JsonProperty("target")
    private ProjectDetail target;

    /**
     * 最后提交信息
     */
    @JsonProperty("last_commit")
    private LastCommitDetail lastCommit;

    /**
     * 是否为进行中
     */
    @JsonProperty("work_in_progress")
    private boolean workInProgress;

    /**
     * 总时间花费
     */
    @JsonProperty("total_time_spent")
    private Integer totalTimeSpent;

    /**
     * 时间变更
     */
    @JsonProperty("time_change")
    private Integer timeChange;

    /**
     * 人类可读的总时间花费
     */
    @JsonProperty("human_total_time_spent")
    private String humanTotalTimeSpent;

    /**
     * 人类可读的时间变更
     */
    @JsonProperty("human_time_change")
    private String humanTimeChange;

    /**
     * 人类可读的时间估算
     */
    @JsonProperty("human_time_estimate")
    private String humanTimeEstimate;

    /**
     * 分配人ID列表
     */
    @JsonProperty("assignee_ids")
    private List<Integer> assigneeIds;

    /**
     * 审阅人ID列表
     */
    @JsonProperty("reviewer_ids")
    private List<Object> reviewerIds;

    /**
     * 标签列表
     */
    @JsonProperty("labels")
    private List<Object> labels;

    /**
     * 状态
     */
    @JsonProperty("state")
    private String state;

    /**
     * 阻塞讨论已解决
     */
    @JsonProperty("blocking_discussions_resolved")
    private boolean blockingDiscussionsResolved;

    /**
     * 是否是第一次贡献
     */
    @JsonProperty("first_contribution")
    private boolean firstContribution;

    /**
     * 详细合并状态
     */
    @JsonProperty("detailed_merge_status")
    private String detailedMergeStatus;

    /**
     * 操作
     */
    @JsonProperty("action")
    private String action;

    /**
     * 旧修订版
     */
    @JsonProperty("oldrev")
    private String oldrev;
}
