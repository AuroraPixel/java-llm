package com.hailiang.langchain4jdemo.pojo.gitlab.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 工程信息详情
 */
@Data
public class ProjectDetail {
    /**
     * 项目ID
     */
    @JsonProperty("id")
    private int id;

    /**
     * 项目名称
     */
    @JsonProperty("name")
    private String name;

    /**
     * 描述
     */
    @JsonProperty("description")
    private String description;

    /**
     * 项目网页URL
     */
    @JsonProperty("web_url")
    private String webUrl;

    /**
     * 头像URL
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;

    /**
     * Git SSH URL
     */
    @JsonProperty("git_ssh_url")
    private String gitSshUrl;

    /**
     * Git HTTP URL
     */
    @JsonProperty("git_http_url")
    private String gitHttpUrl;

    /**
     * 命名空间
     */
    @JsonProperty("namespace")
    private String namespace;

    /**
     * 可见性级别
     */
    @JsonProperty("visibility_level")
    private int visibilityLevel;

    /**
     * 带命名空间的路径
     */
    @JsonProperty("path_with_namespace")
    private String pathWithNamespace;

    /**
     * 默认分支
     */
    @JsonProperty("default_branch")
    private String defaultBranch;

    /**
     * CI配置路径
     */
    @JsonProperty("ci_config_path")
    private String ciConfigPath;

    /**
     * 主页
     */
    @JsonProperty("homepage")
    private String homepage;

    /**
     * URL
     */
    @JsonProperty("url")
    private String url;

    /**
     * SSH URL
     */
    @JsonProperty("ssh_url")
    private String sshUrl;

    /**
     * HTTP URL
     */
    @JsonProperty("http_url")
    private String httpUrl;
}
