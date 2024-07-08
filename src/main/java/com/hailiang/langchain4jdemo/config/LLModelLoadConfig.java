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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@AllArgsConstructor
@EnableConfigurationProperties(LLModelProperties.class)
@Configuration(proxyBeanMethods = false)
@Slf4j
public class LLModelLoadConfig {

    @Bean
    @Lazy
    public ChatLanguageModel initChatLanguageModel(LLModelProperties llModelProperties) {
        log.info("加载ChatModel:{}",llModelProperties.getModelName());
        return loadModel(llModelProperties);
    }

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
        log.warn("LLModel:{}","未有匹配的供应商");
        return null;
    }
}
