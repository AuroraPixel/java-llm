package com.hailiang.langchain4jdemo.config;

import cn.hutool.core.util.StrUtil;
import com.hailiang.langchain4jdemo.config.proerties.EmbeddingModelProperties;
import dev.langchain4j.model.embedding.EmbeddingModel;
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
        if(StrUtil.isEmpty(properties.getProviderName())){
            log.warn("EmbeddingModel:{}","请配置供应商");
            return null;
        }
        if(StrUtil.isEmpty(properties.getApiKey())){
            log.warn("EmbeddingModel:{}","请配置apiKey");
            return null;
        }
        if(StrUtil.isEmpty(properties.getBaseUrl())){
            log.warn("EmbeddingModel:{}","请配置baseUrl");
            return null;
        }

        if (properties.getProviderName().equals("openai")) {
            return OpenAiEmbeddingModel.builder().apiKey(properties.getApiKey())
                    .baseUrl(properties.getBaseUrl())
                    .build();
        }
        log.warn("EmbeddingModel:{}","未有匹配的供应商");
        return null;
    }
}
