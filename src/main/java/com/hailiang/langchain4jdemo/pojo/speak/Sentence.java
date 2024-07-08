package com.hailiang.langchain4jdemo.pojo.speak;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

import java.util.List;

@Data
public class Sentence {
    // 句子ID
    private Long sentenceId;
    // 句子开始时间
    private Long start;
    // 句子结束时间
    private Long end;
    // 句子中的单词列表
    private List<Word> wordList;

    public String getText(){
        if(CollUtil.isEmpty(wordList)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Word word : wordList) {
            sb.append(word.getText());
        }
        return sb.toString();
    }
}
