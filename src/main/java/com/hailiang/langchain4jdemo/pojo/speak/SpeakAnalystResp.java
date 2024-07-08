package com.hailiang.langchain4jdemo.pojo.speak;


import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SpeakAnalystResp {
    @Description("Analysis content list")
    private List<AnalystItem> analystItems;

    @Data
    @ToString
    static class AnalystItem{
        @Description("theme")
        private String theme;
        @Description("List of speakers")
        private List<SpeakUser> speakUsers;
        @Description("Overview of content")
        private String content;
        @Description("Merge start timestamp")
        private Long mergeStartTime;
        @Description("Merge completion timestamp")
        private Long mergeEndTime;
    }

    @Data
    @ToString
    static class SpeakUser{
        @Description("Spokesperson ID")
        private Long id;
        @Description("Start timestamp")
        private Long startTime;
        @Description("End timestamp")
        private Long endTime;
    }
}
