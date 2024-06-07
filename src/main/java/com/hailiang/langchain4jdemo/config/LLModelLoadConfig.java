package com.hailiang.langchain4jdemo.config;


import cn.hutool.core.util.StrUtil;
import com.hailiang.langchain4jdemo.config.proerties.LLModelProperties;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
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
        if(StrUtil.isEmpty(properties.getProviderName())){
            log.warn("LLModel:{}","请配置供应商");
            return null;
        }
        if(StrUtil.isEmpty(properties.getApiKey())){
            log.warn("LLModel:{}","请配置apiKey");
            return null;
        }
        if(StrUtil.isEmpty(properties.getBaseUrl())){
            log.warn("LLModel:{}","请配置baseUrl");
            return null;
        }
        if(StrUtil.isEmpty(properties.getModelName())){
            log.warn("LLModel:{}","请配置modelName");
            return null;
        }
        if(properties.getProviderName().equals("openai")){
            return OpenAiStreamingChatModel.builder().modelName(properties.getModelName())
                    .apiKey(properties.getApiKey()).baseUrl(properties.getBaseUrl()).build();
        }
        log.warn("LLModel:{}","未有匹配的供应商");
        return null;
    }

    private ChatLanguageModel loadModel(LLModelProperties properties) {
        if(StrUtil.isEmpty(properties.getProviderName())){
            log.warn("LLModel:{}","请配置供应商");
            return null;
        }
        if(StrUtil.isEmpty(properties.getApiKey())){
            log.warn("LLModel:{}","请配置apiKey");
            return null;
        }
        if(StrUtil.isEmpty(properties.getBaseUrl())){
            log.warn("LLModel:{}","请配置baseUrl");
            return null;
        }
        if(StrUtil.isEmpty(properties.getModelName())){
            log.warn("LLModel:{}","请配置modelName");
            return null;
        }
        if(properties.getProviderName().equals("openai")){
            return OpenAiChatModel.builder().modelName(properties.getModelName())
                    .apiKey(properties.getApiKey()).baseUrl(properties.getBaseUrl()).build();
        }
        log.warn("LLModel:{}","未有匹配的供应商");
        return null;
    }
}
