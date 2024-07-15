package com.hailiang.langchain4jdemo.config;


import com.hailiang.langchain4jdemo.config.proerties.LLModelProperties;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.qianfan.QianfanChatModel;
import dev.langchain4j.model.qianfan.QianfanStreamingChatModel;
import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel;
import dev.langchain4j.model.vertexai.VertexAiGeminiStreamingChatModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@EnableConfigurationProperties(LLModelProperties.class)
@Configuration(proxyBeanMethods = false)
@Slf4j
public class LLModelLoadConfig {

//    @Bean
//    @Lazy
//    public ChatLanguageModel initChatLanguageModel(LLModelProperties llModelProperties) {
//        log.info("加载ChatModel:{}",llModelProperties.getModelName());
//        return loadModel(llModelProperties);
//    }

    @Bean
    @Lazy
    public StreamingChatLanguageModel initStreamingChatLanguageModel(LLModelProperties llModelProperties) {
        log.info("加载StreamingChatModel:{}",llModelProperties.getModelName());
        return loadStreamingModel(llModelProperties);
    }

    private StreamingChatLanguageModel loadStreamingModel(LLModelProperties properties) {
        //TODO 模型参数校验

        //openai
        if(properties.getProviderName().equals("openai")){
            return OpenAiStreamingChatModel.builder().modelName(properties.getModelName())
                    .apiKey(properties.getApiKey()).baseUrl(properties.getBaseUrl()).build();
        }
        //qianfan
        if(properties.getProviderName().equals("qianfan")){
            return QianfanStreamingChatModel.builder().modelName(properties.getModelName())
                    .apiKey(properties.getApiKey()).baseUrl(properties.getBaseUrl()).build();
        }
        //ollama
        if(properties.getProviderName().equals("ollma")){
            return OllamaStreamingChatModel.builder().
                    modelName(properties.getModelName()).baseUrl(properties.getBaseUrl()).build();
        }
        //google
        if(properties.getProviderName().equals("google")){
            return VertexAiGeminiStreamingChatModel.builder()
                    .project("elevated-heaven-429203-g3")
                    .location("asia-east2")
                    .modelName("gemini-1.5-flash")
                    .build();
        }
        log.warn("LLModel:{}","未有匹配的供应商");
        return null;
    }

    private ChatLanguageModel loadModel(LLModelProperties properties) {
        //TODO 模型参数校验

        //openai
        if(properties.getProviderName().equals("openai")){
            return OpenAiChatModel.builder().modelName(properties.getModelName())
                    .apiKey(properties.getApiKey()).baseUrl(properties.getBaseUrl())
                    .responseFormat("{\"type\": \"json_object\"}")
                    .build();
        }
        //qianfan
        if(properties.getProviderName().equals("qianfan")){
            return QianfanChatModel.builder().modelName(properties.getModelName())
                    .apiKey(properties.getApiKey()).build();
        }
        //ollama
        if(properties.getProviderName().equals("ollma")){
            return OllamaChatModel.builder().
                    modelName(properties.getModelName()).baseUrl(properties.getBaseUrl()).build();
        }
        //google
        if(properties.getProviderName().equals("google")){
            return VertexAiGeminiChatModel.builder()
                    .project("elevated-heaven-429203-g3")
                    .location("asia-east2")
                    .modelName("gemini-1.5-pro")
                    .build();
        }
        log.warn("LLModel:{}","未有匹配的供应商");
        return null;
    }

    public void setEnv(String key, String value) {
        try {
            Map<String, String> env = System.getenv();
            Class<?> cl = env.getClass();
            Field field = cl.getDeclaredField("m");
            field.setAccessible(true);
            Map<String, String> writableEnv = (Map<String, String>) field.get(env);
            writableEnv.put(key, value);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to set environment variable", e);
        }
    }


    public Map<String, ChatLanguageModel> chatLanguageModels() {
        //设置系统环境变量
//        setEnv("GOOGLE_APPLICATION_CREDENTIALS","/Users/hljy/google_token/google_token_wang.json");
//        String wangProject = "golden-object-429501-a6";
        setEnv("GOOGLE_APPLICATION_CREDENTIALS","/Users/hljy/google_token/google_token_ww.json");
        String wwProject = "elevated-heaven-429203-g3";

        List<String> locations = Stream.of("northamerica-northeast1", "southamerica-east1", "us-central1", "us-east1", "us-east4", "us-east5", "us-south1", "us-west1", "us-west4", "europe-central2", "europe-north1", "europe-southwest1") // 添加所有20多个location
                .collect(Collectors.toList());
        return locations.stream()
                .collect(Collectors.toMap(
                        location -> location,
                        location -> VertexAiGeminiChatModel.builder()
                                .project(wwProject)
                                .location(location)
                                .modelName("gemini-1.5-flash")
                                .build()
                ));
    }

    @Bean
    @Primary
    public ChatLanguageModel loadBalancedChatLanguageModelBean() {
        return new loadBalancedChatLanguageModelBean(chatLanguageModels());
    }
}
