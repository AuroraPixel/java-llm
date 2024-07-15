package com.hailiang.langchain4jdemo.config;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class loadBalancedChatLanguageModelBean implements ChatLanguageModel {
    private final Map<String, ChatLanguageModel> chatLanguageModels;
    private final List<String> locations;
    private final AtomicInteger counter = new AtomicInteger(0);

    public loadBalancedChatLanguageModelBean(Map<String, ChatLanguageModel> chatLanguageModels) {
        this.chatLanguageModels = chatLanguageModels;
        this.locations = new ArrayList<>(chatLanguageModels.keySet());
    }
    @Override
    public Response<AiMessage> generate(List<ChatMessage> messages) {
        int index = counter.getAndUpdate(i -> (i + 1) % locations.size());
        String location = locations.get(index);
        ChatLanguageModel selectedModel = chatLanguageModels.get(location);

        // 打印当前使用的地区
        System.out.println("Using location: " + location);

        return selectedModel.generate(messages);
    }
}
