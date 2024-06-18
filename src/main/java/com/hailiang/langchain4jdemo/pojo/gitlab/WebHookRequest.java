package com.hailiang.langchain4jdemo.pojo.gitlab;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.*;
import lombok.Data;

import java.util.List;

/**
 * webhook请求
 */
@Data
public class WebHookRequest {
    /**
     * 对象类型
     */
    @JsonProperty("object_kind")
    private String objectKind;

    /**
     * 事件类型
     */
    @JsonProperty("event_type")
    private String eventType;

    /**
     * 用户信息
     */
    @JsonProperty("user")
    private GitUserDetail user;

    /**
     * 项目信息
     */
    @JsonProperty("project")
    private ProjectDetail project;

    /**
     * 对象属性
     */
    @JsonProperty("object_attributes")
    private ObjectAttributesDetail objectAttributes;

    /**
     * 标签
     */
    @JsonProperty("labels")
    private List<Object> labels;

    /**
     * 变更信息
     */
    @JsonProperty("changes")
    private ChangesDetail changes;

    /**
     * 仓库信息
     */
    @JsonProperty("repository")
    private RepositoryDetail repository;

    /**
     * 分配人列表
     */
    @JsonProperty("assignees")
    private List<GitUserDetail> assignees;
}
