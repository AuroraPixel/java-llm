package com.hailiang.langchain4jdemo.agent;


import com.hailiang.langchain4jdemo.prompt.CustomerSupport;
import com.hailiang.langchain4jdemo.tools.BookingTools;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 客服代理
 */
@Component
public class CustomerSupportAgent {

    /**
     * 创建retriever
     * @param embeddingStore
     * @param embeddingModel
     * @return
     */
    @Bean
    Retriever<TextSegment> retriever(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {
        //最大搜索结果
        int maxResultsRetrieved = 5;
        //最小匹配得分
        double minScore = 0.8;
        return EmbeddingStoreRetriever.from(embeddingStore, embeddingModel, maxResultsRetrieved, minScore);
    }

    /**
     * 创建客服代理
     */
    @Bean
    CustomerSupport customerSupport(ChatLanguageModel chatLanguageModel, BookingTools bookingTools, Retriever<TextSegment> retriever) {
        return AiServices.builder(CustomerSupport.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(bookingTools)
                .retriever(retriever)
                .build();
    }
}
