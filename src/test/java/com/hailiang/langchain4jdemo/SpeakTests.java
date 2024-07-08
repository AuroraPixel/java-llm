package com.hailiang.langchain4jdemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hailiang.langchain4jdemo.agent.SpeakAnalystAgent;
import com.hailiang.langchain4jdemo.pojo.speak.Paragraph;
import com.hailiang.langchain4jdemo.pojo.speak.SpeakAnalystResp;
import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class SpeakTests {
    @Autowired
    private SpeakAnalystAgent agent;
    @Autowired
    private ChatLanguageModel chatLanguageModel;

    @Test
    void TestLoadJsonToObject(){
        List<Paragraph> paragraphs = loadJsonToObject(new TypeReference<List<Paragraph>>() {
        }, "/example-files/asr_result.json");
        for (Paragraph paragraph : paragraphs) {
            System.out.println(paragraph.getTextDetail());
        }
    }

    @Test
    void TestSpeakAnalyst(){
        List<Paragraph> paragraphs = loadJsonToObject(new TypeReference<List<Paragraph>>() {
        }, "/example-files/asr_result_1.json");
        List<String> messages = paragraphs.stream().map(Paragraph::getTextDetail).collect(Collectors.toList());
        String sut = agent.speakAnalyse(messages);
        System.out.println(sut);
    }

    @Test
    void TestSpeakAnalystPojo(){
        List<Paragraph> paragraphs = loadJsonToObject(new TypeReference<List<Paragraph>>() {
        }, "/example-files/asr_result.json");
        List<String> messages = paragraphs.stream().map(Paragraph::getTextDetail).collect(Collectors.toList());
        //计算token
        OpenAiChatModel aiChatModel = (OpenAiChatModel) chatLanguageModel;
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new SystemMessage("1.You are a professional expert in analyzing voice conversations and will only respond to content related to voice conversation analysis."));
        chatMessages.add(new SystemMessage("2.Analyze the voice conversation text, identify different topics, and merge similar content. Then provide a summary of each topic and the merged time intervals."));
        chatMessages.add(new SystemMessage("3.The output must be in JSON format, and any non-JSON content is strictly prohibited. Only output { ... } and avoid using markdown format such as \"```json......```\"."));
        List<ChatMessage> speakMessages = messages.stream().map(UserMessage::new).collect(Collectors.toList());
        chatMessages.addAll(speakMessages);
        chatMessages.add(new UserMessage("The output must be in JSON format, and any non-JSON content is strictly prohibited. Only output { ... }"));
        chatMessages.add(new UserMessage("Avoid using markdown format such as \"```json......```\"."));
        Response<AiMessage> generate = aiChatModel.generate(chatMessages);
        System.out.println(generate.content().text());
//        int token = aiChatModel.estimateTokenCount(messages.toString());
//        System.out.println(token);
//        SpeakAnalystResp sut = agent.speakAnalyseWithPojo(messages);
//        System.out.println(sut.toString());
    }

    public <T> T loadJsonToObject(TypeReference<T> typeReference, String path) {
        // 创建ObjectMapper实例
        ObjectMapper objectMapper = new ObjectMapper();
        // 获取JSON文件的输入流
        ClassPathResource resource = new ClassPathResource(path);
        InputStream inputStream;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 将JSON文件内容转换为指定类型的对象
        try {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
