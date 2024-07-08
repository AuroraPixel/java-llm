package com.hailiang.langchain4jdemo.pojo.speak;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

import java.util.List;

@Data
public class Paragraph {
    // 段落ID
    private String paragraphId;
    // 发言人ID
    private String speakerId;
    // 发言人姓名
    private String speakerName;
    // 段落开始时间
    private Long start;
    // 段落结束时间
    private Long end;
    // 段落中的句子列表
    private List<Sentence> sentenceList;

    public String getText() {
        if(CollUtil.isEmpty(sentenceList)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sentenceList.size(); i++) {
            sb.append(sentenceList.get(i).getText());
            if(i < sentenceList.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String getTextDetail(){
        if(CollUtil.isEmpty(sentenceList)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("发言人ID:").append(speakerId).append("\n");
        sb.append("时间:").append(start).append("~").append(end).append("\n");
        for (Sentence sentence : sentenceList) {
            sb.append(sentence.getText());
            sb.append("\n");
        }
        return sb.toString();
    }
}
