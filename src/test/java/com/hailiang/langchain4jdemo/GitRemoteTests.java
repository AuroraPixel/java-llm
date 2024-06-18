package com.hailiang.langchain4jdemo;

import cn.hutool.core.collection.ListUtil;
import com.hailiang.langchain4jdemo.prompt.*;
import com.hailiang.langchain4jdemo.remote.GitLabRemote;
import com.hailiang.langchain4jdemo.response.CharacterAnalysis;
import com.hailiang.langchain4jdemo.response.InputReview;
import com.hailiang.langchain4jdemo.response.NumberAndDateExtractor;
import com.hailiang.langchain4jdemo.response.SentimentAnalyzer;
import com.hailiang.langchain4jdemo.response.enu.SentimentEnum;
import com.hailiang.langchain4jdemo.response.pojo.Person;
import com.hailiang.langchain4jdemo.response.pojo.Persons;
import com.hailiang.langchain4jdemo.tools.Calculator;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.elasticsearch.ElasticsearchEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@SpringBootTest
class GitRemoteTests {
    @Autowired
    private GitLabRemote gitLabRemote;

    @Test
    void TestGitLabRemoteCompare(){
        String compare = gitLabRemote.compare("915","7041330bb734a35697fa91c7e722337bbe530bc2", "6fb5003685bd45c57db3394437030a8ca234b75b");
        System.out.println(compare);
    }

    @Test
    void TestGitLabRemoteCommits(){
        String compare = gitLabRemote.getCommits("915","7041330bb734a35697fa91c7e722337bbe530bc2");
        System.out.println(compare);
    }


    @Test
    void TestGitLabRemoteSingleMR(){
        String compare = gitLabRemote.getSingleMR("915","4");
        System.out.println(compare);
    }
}
