package com.hailiang.langchain4jdemo.pojo.gitlab.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 变更详情
 */
@Data
public class ChangesDetail {
    /**
     * 更新时间变更
     */
    @JsonProperty("updated_at")
    private ChangeDetails updatedAt;

    @Data
    public static class ChangeDetails {
        /**
         * 以前的值
         */
        @JsonProperty("previous")
        private String previous;

        /**
         * 当前的值
         */
        @JsonProperty("current")
        private String current;
    }
}
