package com.hailiang.langchain4jdemo.pojo.gitlab.detail;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * gitlab用户详情
 */
@Data
public class GitUserDetail {
    /**
     * 用户ID
     */
    @JsonProperty("id")
    private int id;

    /**
     * 用户名
     */
    @JsonProperty("username")
    private String username;

    /**
     * 姓名
     */
    @JsonProperty("name")
    private String name;

    /**
     * 状态
     */
    @JsonProperty("state")
    private String state;

    /**
     * 是否锁定
     */
    @JsonProperty("locked")
    private boolean locked;

    /**
     * 头像URL
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;

    /**
     * 邮箱
     */
    @JsonProperty("email")
    private String email;

    /**
     * 网页URL
     */
    @JsonProperty("web_url")
    private String webUrl;

    /**
     * 是否可以合并
     */
    @JsonProperty("can_merge")
    private Boolean canMerge;
}
