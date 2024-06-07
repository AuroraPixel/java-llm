package com.hailiang.langchain4jdemo.config.proerties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(
    prefix = "hailiang.langchain4j.llm",
        ignoreUnknownFields = true
)
@Getter
@Setter
public class LLModelProperties {
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
     * modelName
     */
    private String modelName;
}
