package com.hailiang.langchain4jdemo;

import com.hailiang.langchain4jdemo.config.proerties.EmbeddingModelProperties;
import com.hailiang.langchain4jdemo.config.proerties.EmbeddingStoreProperties;
import com.hailiang.langchain4jdemo.config.proerties.LLModelProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({LLModelProperties.class, EmbeddingModelProperties.class, EmbeddingStoreProperties.class})
public class Langchain4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Langchain4jApplication.class, args);
    }

}
