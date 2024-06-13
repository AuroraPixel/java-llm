package com.hailiang.langchain4jdemo.config;

import cn.hutool.core.util.StrUtil;
import com.hailiang.langchain4jdemo.config.proerties.EmbeddingModelProperties;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


@AllArgsConstructor
@EnableConfigurationProperties(EmbeddingModelProperties.class)
@Configuration(proxyBeanMethods = false)
@Slf4j
public class EmbeddingModelLoadConfig {

    @Bean
    @Lazy
    public EmbeddingModel initEmbeddingModel(EmbeddingModelProperties properties) {
        log.info("加载EmbeddingModel:{}",properties.getProviderName());
        return loadEmbeddingModel(properties);
    }

    private EmbeddingModel loadEmbeddingModel(EmbeddingModelProperties properties) {
        //TODO 模型参数校验
        if (properties.getProviderName().equals("openai")) {
            return OpenAiEmbeddingModel.builder().apiKey(properties.getApiKey())
                    .baseUrl(properties.getBaseUrl())
                    .build();
        }
        //Ollama
        if (properties.getProviderName().equals("ollma")) {
            return OllamaEmbeddingModel.builder().baseUrl(properties.getBaseUrl()).modelName(properties.getModelName()).build();
        }
        log.warn("EmbeddingModel:{}","未有匹配的供应商");
        return null;
    }
}
