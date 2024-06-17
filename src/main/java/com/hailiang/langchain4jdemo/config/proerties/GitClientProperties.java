package com.hailiang.langchain4jdemo.config.proerties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "hailiang.git.client",
        ignoreUnknownFields = true
)
@Getter
@Setter
public class GitClientProperties {
    /**
     * 供应:gitlab,gitee
     */
    private String providerName;
    /**
     * url
     */
    private String url;
    /**
     * token地址
     */
    private String token;
}
