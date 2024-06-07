package com.hailiang.langchain4jdemo.component;

import org.springframework.stereotype.Component;

@Component
public class LLModelLoadComponent {
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
