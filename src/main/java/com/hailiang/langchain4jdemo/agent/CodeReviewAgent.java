package com.hailiang.langchain4jdemo.agent;

import com.hailiang.langchain4jdemo.prompt.CodeReview;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CodeReviewAgent {
    @Autowired
    private ChatLanguageModel chatLanguageModel;
    public String codeReview(List<String> codeList){
        CodeReview codeReviewAiServices = AiServices.builder(CodeReview.class).chatLanguageModel(chatLanguageModel)
                //.contentRetriever(contentRetriever)
                .build();
        return codeReviewAiServices.codeReview(codeList);
    }
}
