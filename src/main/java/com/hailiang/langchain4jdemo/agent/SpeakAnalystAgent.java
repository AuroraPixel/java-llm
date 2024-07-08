package com.hailiang.langchain4jdemo.agent;


import com.hailiang.langchain4jdemo.pojo.speak.SpeakAnalystResp;
import com.hailiang.langchain4jdemo.prompt.SpeakAnalyst;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SpeakAnalystAgent {
    @Autowired
    private ChatLanguageModel chatLanguageModel;
    public String speakAnalyse(List<String> message){
        SpeakAnalyst speakAnalyst = AiServices.builder(SpeakAnalyst.class).chatLanguageModel(chatLanguageModel)
                //.contentRetriever(contentRetriever)
                .build();
        return speakAnalyst.summaryAnalyst(message);
    }

    public SpeakAnalystResp speakAnalyseWithPojo(List<String> message){
        SpeakAnalyst speakAnalyst = AiServices.builder(SpeakAnalyst.class).chatLanguageModel(chatLanguageModel)
                //.contentRetriever(contentRetriever)
                .build();
        return speakAnalyst.speakAnalystWithPojo(message);
    }
}
