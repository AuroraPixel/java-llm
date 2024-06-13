package com.hailiang.langchain4jdemo.config.proerties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(
    prefix = "hailiang.langchain4j.embedding",
        ignoreUnknownFields = true
)
@Getter
@Setter
public class EmbeddingModelProperties {
    /**
     * 供应商名字
     */
    private String providerName;

    /**
     * apiKey
     */
    private String apiKey;

    /**
     * baseUrl
     */
    private String baseUrl;

    /**
     * 模型名字
     */
    private String modelName;
}
