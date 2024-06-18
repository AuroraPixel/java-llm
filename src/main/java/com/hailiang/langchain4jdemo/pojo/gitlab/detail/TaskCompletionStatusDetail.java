package com.hailiang.langchain4jdemo.pojo.gitlab.detail;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 任务完成状态
 */
@Data
public class TaskCompletionStatusDetail {
    /**
     * 任务总数
     */
    @JsonProperty("count")
    private int count;

    /**
     * 完成的任务总数
     */
    @JsonProperty("completed_count")
    private int completedCount;
}
