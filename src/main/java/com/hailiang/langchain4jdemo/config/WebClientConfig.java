package com.hailiang.langchain4jdemo.config;


import com.hailiang.langchain4jdemo.config.proerties.GitClientProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.function.client.WebClient;

@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(GitClientProperties.class)
@Slf4j
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }


    @Bean
    @Qualifier("gitClient")
    @Lazy
    public WebClient gitClient(WebClient.Builder webClientBuilder,GitClientProperties properties) {
        return  webClientBuilder.baseUrl(properties.getUrl())
                .defaultHeader("PRIVATE-TOKEN", properties.getToken()).build();
    }


}
