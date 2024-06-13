package com.hailiang.langchain4jdemo.config;


import cn.hutool.core.util.StrUtil;
import com.hailiang.langchain4jdemo.config.proerties.EmbeddingStoreProperties;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@AllArgsConstructor
@EnableConfigurationProperties(EmbeddingStoreProperties.class)
@Configuration(proxyBeanMethods = false)
@Slf4j
public class EmbeddingStoreLoadConfig {
    @Bean
    @Lazy
    public ElasticsearchEmbeddingStore getDefaultElasticsearchEmbeddingStore(EmbeddingStoreProperties properties) {
        String elasticHost = properties.getElasticHost();
        int elasticPort = properties.getElasticPort();
        String url = StrUtil.format("{}:{}", elasticHost, elasticPort);
        log.info("加载EmbeddingStore:{}",url);
        return ElasticsearchEmbeddingStore.builder()
                .serverUrl(url)
                .userName(properties.getElasticUsername())
                .password(properties.getElasticPassword())
                .indexName(properties.getIndexName())
                .dimension(384)
                .build();
    }
}
