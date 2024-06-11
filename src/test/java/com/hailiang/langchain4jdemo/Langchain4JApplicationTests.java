package com.hailiang.langchain4jdemo;

import cn.hutool.core.collection.ListUtil;
import com.hailiang.langchain4jdemo.prompt.CookingAssistant;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Langchain4JApplicationTests {
    @Autowired
    private ChatLanguageModel chatModel;
    @Autowired
    private StreamingChatLanguageModel streamingChatModel;


    /**
     * 普通简单使用
     */
    @Test
    void TestChatModel() {
        String response = chatModel.generate("你是谁?");
        System.out.println(response);
    }


    /**
     * 流形式的简单使用
     */
    @Test
    void TestStreamChatModel() {
        streamingChatModel.generate("帮我用java写一个冒泡排序", new StreamingResponseHandler<AiMessage>() {
            @Override
            public void onNext(String token) {
                System.out.println("onNext(): " + token);
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                System.out.println("onComplete(): " + response);
            }

            @Override
            public void onError(Throwable error) {
                System.out.println("onError(): " + error);
            }
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 普通动态提示词
     */
    @Test
    void TestPromptTemplate() {
        PromptTemplate promptTemplate = PromptTemplate.from("说 {{context}} 用 {{language}}.");
        Map<String,Object> variables = new HashMap<>();
        variables.put("context","你好");
        variables.put("language","英语");
        Prompt prompt = promptTemplate.apply(variables);


        System.out.println(prompt.text());
    }


    /**
     * 结构化提示词的使用
     */
    @Test
    void TestStructPrompt() {
        CookingAssistant cookingAssistant = new CookingAssistant();
        cookingAssistant.setDish("西红柿炒鸡蛋");
        List<String> ingredients = ListUtil.of("西红柿","鸡蛋");
        cookingAssistant.setIngredients(ingredients);
        Prompt prompt = StructuredPromptProcessor.toPrompt(cookingAssistant);
        AiMessage content = chatModel.generate(prompt.toUserMessage()).content();

        System.out.println(content);
    }


    /**
     * 结构化输出
     */
    @Test
    void TestStructResponse(){

    }

}
