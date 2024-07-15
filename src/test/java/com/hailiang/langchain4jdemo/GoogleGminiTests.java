package com.hailiang.langchain4jdemo;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GoogleGminiTests {
    @Test
    void TestGoogleGmini(){
        ChatLanguageModel chatLanguageModel = VertexAiGeminiChatModel.builder()
                .project("elevated-heaven-429203-g3")
                .location("asia-east2")
                .modelName("gemini-1.5-pro")
                .build();
        String generate = chatLanguageModel.generate("你是谁？");
        System.out.println(generate);
    }

}
