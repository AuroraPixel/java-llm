# Java Langchain使用（LangChain4j） 🚀

## LangChain4j介绍 📚
LangChain4j 于 2023 年初开发，旨在填补 Java 生态系统中 LLM 库的空白，融合了 LangChain、Haystack、LlamaIndex 等的思想和创新元素。

## 核心功能特点 🌟

### 统一 API 管理 🌐
- 支持 10 多个 LLM 提供商
- 向量数据的存储
- 简化服务开发

### 全面工具箱 🛠️
- 提示词模版
- 多模态
- AI 调用工具
- Agent 代理
- RAG 搜索

## 配置 🛠️

### 基础环境依赖 📋
- JDK 17+
- Spring Boot 3.0 以上

### POM 文件依赖 📦

```xml
<!-- langchain4j 核心包 -->
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j</artifactId>
    <version>0.31.0</version>
</dependency>

<!-- langchain4j 的 OpenAI 包 -->
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-open-ai</artifactId>
    <version>0.31.0</version>
</dependency>

<!-- langchain4j 的百度千帆包 -->
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-qianfan</artifactId>
    <version>0.31.0</version>
</dependency>

<!-- langchain4j 的向量模型核心包 -->
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-embeddings</artifactId>
    <version>0.31.0</version>
</dependency>

<!-- langchain4j 的 Elasticsearch 向量模型数据库包 -->
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-elasticsearch</artifactId>
    <version>0.31.0</version>
</dependency>
```
### 注意事项 ⚠️
- 如果使用 JDK 1.8 + Spring Boot 2.0 以上，需要解决 okhttp 依赖问题。

## 核心功能使用

### AI模型的三方API使用（open ai和百度千帆）为例

普通模型的初始化，所有的模型实现都继承了 ChatLanguageModel
```java
    private ChatLanguageModel loadModel(LLModelProperties properties) {
        //TODO 模型参数校验
        
        //openai
        if(properties.getProviderName().equals("openai")){
            return OpenAiChatModel.builder().modelName(properties.getModelName())
                    .apiKey(properties.getApiKey()).baseUrl(properties.getBaseUrl()).build();
        }
        //qianfan
        if(properties.getProviderName().equals("qianfan")){
            return QianfanChatModel.builder().modelName(properties.getModelName())
                    .apiKey(properties.getApiKey()).build();
        }
        log.warn("LLModel:{}","未有匹配的供应商");
        return null;
    }
```
普通文本生成
```java
    //加载模型
    @Autowired
    private ChatLanguageModel chatModel;

    /**
     * 普通简单使用
     */
    @Test
    void TestChatModel() {
        String response = chatModel.generate("你是谁?");
        System.out.println(response);
        String response1 = chatModel.generate("你是openai研发的大模型吗?");
        System.out.println(response1);
    }
```

异步模型的初始化，所有的模型实现都继承了 StreamingChatLanguageModel
```java
    private StreamingChatLanguageModel loadStreamingModel(LLModelProperties properties) {
        //TODO 模型参数校验

        //openai
        if(properties.getProviderName().equals("openai")){
            return OpenAiStreamingChatModel.builder().modelName(properties.getModelName())
                    .apiKey(properties.getApiKey()).baseUrl(properties.getBaseUrl()).build();
        }
        //qianfan
        if(properties.getProviderName().equals("qianfan")){
            return QianfanStreamingChatModel.builder().modelName(properties.getModelName())
                    .apiKey(properties.getApiKey()).baseUrl(properties.getBaseUrl()).build();
        }
        log.warn("LLModel:{}","未有匹配的供应商");
        return null;
    }
```
异步流文本生成
```java
    //加载异步流模型
    @Autowired
    private StreamingChatLanguageModel streamingChatModel;
    /**
     * 流形式的简单使用
     */
    @Test
    void TestStreamChatModel() {
        streamingChatModel.generate("帮我用java写一个冒泡排序", new StreamingResponseHandler<AiMessage>() {
            @Override
            public void onNext(String token) {
                System.out.println("onNext(): " + token);
            }

            @Override
            public void onComplete(Response<AiMessage> response) {
                System.out.println("onComplete(): " + response);
            }

            @Override
            public void onError(Throwable error) {
                System.out.println("onError(): " + error);
            }
        });

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

```


### 工具函数的调用
### Embedding模型使用与向量存储
### RAG搜索（知识库搭建）
### Agent代理
## 扩展
### one-api

