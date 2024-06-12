package com.hailiang.langchain4jdemo.config.proerties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "hailiang.langchain4j.embedding.store",
        ignoreUnknownFields = true
)
@Getter
@Setter
public class EmbeddingStoreProperties {
    /**
     * elasticSearch的地址
     */
    private String elasticHost;
    /**
     * elasticSearch的端口
     */
    private Integer elasticPort;

    /**
     * elasticSearch的用户名
     */
    private String elasticUsername;

    /**
     * elasticSearch的密码
     */
    private String elasticPassword;

    /**
     * 索引名称
     */
    private String indexName;
}
