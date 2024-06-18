package com.hailiang.langchain4jdemo.pojo.gitlab.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 时间统计详情
 */
@Data
public class TimeStatsDetail {
    /**
     * 预估时间
     */
    @JsonProperty("time_estimate")
    private int timeEstimate;

    /**
     * 总时间花费
     */
    @JsonProperty("total_time_spent")
    private int totalTimeSpent;

    /**
     * 人类可读预估时间
     */
    @JsonProperty("human_time_estimate")
    private String humanTimeEstimate;

    /**
     * 人类可读总时间花费
     */
    @JsonProperty("human_total_time_spent")
    private String humanTotalTimeSpent;
}
