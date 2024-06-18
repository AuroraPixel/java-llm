package com.hailiang.langchain4jdemo.pojo.gitlab;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.*;
import lombok.Data;

import java.util.List;

/**
 * 合并请求信息
 */
@Data
public class MergeRequestInfo {
    /**
     * 合并请求ID
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * 内部ID
     */
    @JsonProperty("iid")
    private Integer iid;

    /**
     * 项目ID
     */
    @JsonProperty("project_id")
    private Integer projectId;

    /**
     * 标题
     */
    @JsonProperty("title")
    private String title;

    /**
     * 描述
     */
    @JsonProperty("description")
    private String description;

    /**
     * 状态
     */
    @JsonProperty("state")
    private String state;

    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * 更新时间
     */
    @JsonProperty("updated_at")
    private String updatedAt;

    /**
     * 合并人
     */
    @JsonProperty("merged_by")
    private GitUserDetail mergedBy;

    /**
     * 合并用户
     */
    @JsonProperty("merge_GitUserDetail")
    private GitUserDetail mergeGitUserDetail;

    /**
     * 合并时间
     */
    @JsonProperty("merged_at")
    private String mergedAt;

    /**
     * 关闭人
     */
    @JsonProperty("closed_by")
    private GitUserDetail closedBy;

    /**
     * 关闭时间
     */
    @JsonProperty("closed_at")
    private String closedAt;

    /**
     * 目标分支
     */
    @JsonProperty("target_branch")
    private String targetBranch;

    /**
     * 源分支
     */
    @JsonProperty("source_branch")
    private String sourceBranch;

    /**
     * 用户备注数量
     */
    @JsonProperty("GitUserDetail_notes_count")
    private Integer GitUserDetailNotesCount;

    /**
     * 赞成票数
     */
    @JsonProperty("upvotes")
    private Integer upvotes;

    /**
     * 反对票数
     */
    @JsonProperty("downvotes")
    private Integer downvotes;

    /**
     * 作者
     */
    @JsonProperty("author")
    private GitUserDetail author;

    /**
     * 分配人列表
     */
    @JsonProperty("assignees")
    private List<GitUserDetail> assignees;

    /**
     * 分配人
     */
    @JsonProperty("assignee")
    private GitUserDetail assignee;

    /**
     * 审阅人列表
     */
    @JsonProperty("reviewers")
    private List<GitUserDetail> reviewers;

    /**
     * 源项目ID
     */
    @JsonProperty("source_project_id")
    private Integer sourceProjectId;

    /**
     * 目标项目ID
     */
    @JsonProperty("target_project_id")
    private Integer targetProjectId;

    /**
     * 标签列表
     */
    @JsonProperty("labels")
    private List<String> labels;

    /**
     * 是否为草稿
     */
    @JsonProperty("draft")
    private boolean draft;

    /**
     * 是否为进行中
     */
    @JsonProperty("work_in_progress")
    private boolean workInProgress;

    /**
     * 里程碑
     */
    @JsonProperty("milestone")
    private String milestone;

    /**
     * 当管道成功时合并
     */
    @JsonProperty("merge_when_pipeline_succeeds")
    private boolean mergeWhenPipelineSucceeds;

    /**
     * 合并状态
     */
    @JsonProperty("merge_status")
    private String mergeStatus;

    /**
     * 详细合并状态
     */
    @JsonProperty("detailed_merge_status")
    private String detailedMergeStatus;

    /**
     * SHA
     */
    @JsonProperty("sha")
    private String sha;

    /**
     * 合并提交SHA
     */
    @JsonProperty("merge_commit_sha")
    private String mergeCommitSha;

    /**
     * 压缩提交SHA
     */
    @JsonProperty("squash_commit_sha")
    private String squashCommitSha;

    /**
     * 讨论锁定
     */
    @JsonProperty("discussion_locked")
    private Boolean discussionLocked;

    /**
     * 是否应删除源分支
     */
    @JsonProperty("should_remove_source_branch")
    private Boolean shouldRemoveSourceBranch;

    /**
     * 强制删除源分支
     */
    @JsonProperty("force_remove_source_branch")
    private boolean forceRemoveSourceBranch;

    /**
     * 准备时间
     */
    @JsonProperty("prepared_at")
    private String preparedAt;

    /**
     * 参考
     */
    @JsonProperty("reference")
    private String reference;

    /**
     * 参考信息
     */
    @JsonProperty("references")
    private ReferencesDetail references;

    /**
     * 网页URL
     */
    @JsonProperty("web_url")
    private String webUrl;

    /**
     * 时间统计
     */
    @JsonProperty("time_stats")
    private TimeStatsDetail timeStats;

    /**
     * 是否压缩
     */
    @JsonProperty("squash")
    private boolean squash;

    /**
     * 合并时压缩
     */
    @JsonProperty("squash_on_merge")
    private boolean squashOnMerge;

    /**
     * 任务完成状态
     */
    @JsonProperty("task_completion_status")
    private TaskCompletionStatusDetail taskCompletionStatus;

    /**
     * 是否有冲突
     */
    @JsonProperty("has_conflicts")
    private boolean hasConflicts;

    /**
     * 阻塞讨论已解决
     */
    @JsonProperty("blocking_discussions_resolved")
    private boolean blockingDiscussionsResolved;

    /**
     * 是否订阅
     */
    @JsonProperty("subscribed")
    private boolean subscribed;

    /**
     * 更改计数
     */
    @JsonProperty("changes_count")
    private String changesCount;

    /**
     * 最新构建开始时间
     */
    @JsonProperty("latest_build_started_at")
    private String latestBuildStartedAt;

    /**
     * 最新构建完成时间
     */
    @JsonProperty("latest_build_finished_at")
    private String latestBuildFinishedAt;

    /**
     * 第一次部署到生产环境时间
     */
    @JsonProperty("first_deployed_to_production_at")
    private String firstDeployedToProductionAt;

    /**
     * 管道
     */
    @JsonProperty("pipeline")
    private Object pipeline;

    /**
     * 头管道
     */
    @JsonProperty("head_pipeline")
    private Object headPipeline;

    /**
     * 差异引用
     */
    @JsonProperty("diff_refs")
    private DiffRefsDetail diffRefsDetail;

    /**
     * 合并错误
     */
    @JsonProperty("merge_error")
    private String mergeError;

    /**
     * 是否是第一次贡献
     */
    @JsonProperty("first_contribution")
    private boolean firstContribution;

    /**
     * 用户信息
     */
    @JsonProperty("GitUserDetail")
    private GitUserDetail GitUserDetail;
}
