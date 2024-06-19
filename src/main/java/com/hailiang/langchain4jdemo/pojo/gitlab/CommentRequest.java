package com.hailiang.langchain4jdemo.pojo.gitlab;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentRequest {
    /**
     * 评论内容
     */
    private String body;

    /**
     * 项目id
     */
    private Integer id;

    /**
     * mergeRequestId
     */
    @JsonProperty("merge_request_id")
    private Integer mergeRequestId;
}
