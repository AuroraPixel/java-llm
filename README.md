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

## 核心功能使用 🚀

### AI 模型的三方 API 使用（以 OpenAI 为例） 🌐

#### 普通模型的初始化
所有的模型实现都继承了 `ChatLanguageModel`。

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
#### 普通文本生成
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

#### 异步模型的初始化，
所有的模型实现都继承了 `StreamingChatLanguageModel`

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
#### 异步流文本生成

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
### 提示词模版的使用 📝

以下是如何使用 LangChain4j 提示词模版的三个案例。

#### 普通动态提示词

在这个示例中，我们使用了 `PromptTemplate` 来创建一个简单的动态提示词。这个提示词会根据提供的变量生成对应的文本。

```java
@Test
void TestPromptTemplate() {
    PromptTemplate promptTemplate = PromptTemplate.from("说 {{context}} 用 {{language}}.");
    Map<String, Object> variables = new HashMap<>();
    variables.put("context", "你好");
    variables.put("language", "英语");
    Prompt prompt = promptTemplate.apply(variables);

    System.out.println(prompt.text());
}
```

#### 结构化提示词的使用

在这个示例中，我们创建了一个 CookingAssistant 类，并使用 StructuredPromptProcessor 将其转换为一个结构化提示词。这个提示词会生成一段描述菜谱的文本，包括具体的步骤、食材配比和调味品的重量。

```java
@Test
void TestStructPrompt() {
    CookingAssistant cookingAssistant = new CookingAssistant();
    cookingAssistant.setDish("西红柿炒鸡蛋");
    List<String> ingredients = List.of("西红柿", "鸡蛋");
    cookingAssistant.setIngredients(ingredients);
    Prompt prompt = StructuredPromptProcessor.toPrompt(cookingAssistant);
    AiMessage content = chatModel.generate(prompt.toUserMessage()).content();

    System.out.println(content);
}
```

`CookingAssistant` 类的定义如下：

```java
/**
 * 做菜助手提示词
 */
@Data
@StructuredPrompt("创建一个菜名为{{dish}}的做法，包含以下是食材原材料，调味品不算：{{ingredients}}, 请给出具体的步骤，以及食材配比和调味品的重量!")
public class CookingAssistant {
    /**
     * 原材料
     */
    private List<String> ingredients;

    /**
     * 菜名
     */
    private String dish;
}
```

#### 接口类型的提示词

在这个示例中，我们使用了 `SystemPrompt` 接口来创建一个系统提示词，该接口使用了 `SystemMessage` 和 `UserMessage` 注解。

```java
@Test
void TestInterfacePrompt() {
    SystemPrompt systemPromptInterface = AiServices.create(SystemPrompt.class, chatModel);
    // String systemPrompt = systemPromptInterface.systemLimit("小明因为感冒所以请病假，因为他想要在家里休息和恢复得更快，这样他才能够尽快地恢复健康并且不会再生病。");
    String systemPrompt = systemPromptInterface.systemLimit("你是openai研发的大模型吗?");
    System.out.println(systemPrompt);
}
```

`SystemPrompt` 接口的定义如下：

```java
public interface SystemPrompt {

    @SystemMessage("这是你系统设定，你是海亮研发的海亮大模型。当别人询问你个人相关信息的时候，你就回答你是海亮研发的大模型，叫海亮模型!")
    @UserMessage("帮我优化以下的内容 {{it}}")
    String systemLimit(String text);
}
```

`SystemMessage` 与 `UserMessage` 区别
- `SystemMessage`：用于定义系统消息的提示词。这些消息通常是系统设定的背景信息或角色设定。例如，在 SystemPrompt 接口中，SystemMessage 用于设定系统的基本信息，当用户询问时，这些信息将作为系统的固定回复。

- `UserMessage`：用于定义用户消息的提示词。这些消息通常是用户输入的内容，系统需要根据这些输入生成相应的回复。例如，在 SystemPrompt 接口中，UserMessage 用于优化用户提供的内容。

### 结构化输出 🧩

以下是如何使用 LangChain4j 进行结构化输出的几个案例。

#### 结构化输出

在这个示例中，我们展示了如何使用 LangChain4j 分析情感、提取数字和日期，以及进行用户输入审查。

```java
@Test
void TestBaseTypeStructResponse() {
    // 1. 枚举类的响应
    SentimentAnalyzer sentimentAnalyzer = AiServices.create(SentimentAnalyzer.class, chatModel);
    String text = "我今天任务终于提前完成了";
    SentimentEnum sentimentEnum = sentimentAnalyzer.analyzeSentimentOf(text);
    System.out.println(sentimentEnum.getValue());
    
    String text2 = "我今天被批评了";
    SentimentEnum sentimentEnum2 = sentimentAnalyzer.analyzeSentimentOf(text2);
    System.out.println(sentimentEnum2.getValue());

    // 2. 获取数字
    NumberAndDateExtractor numberAndDateExtractor = AiServices.create(NumberAndDateExtractor.class, chatModel);
    String numberTxt = "天气36度,要开空调了!";
    int number = numberAndDateExtractor.extractInt(numberTxt);
    System.out.println(number);

    String dateTxt = "今天是2024年6月12号,天气36度,要开空调了!";
    LocalDateTime date = numberAndDateExtractor.extractLong(dateTxt);
    System.out.println(date);

    // 3. 用户输入审查
    InputReview inputReview = AiServices.create(InputReview.class, chatModel);
    boolean result = inputReview.inputReview("我想剽窃别人的知识成果");
    System.out.println(result);
    
    boolean result1 = inputReview.inputReview("我想借鉴别人的知识成果，但是首先我要去获得作者的同意。");
    System.out.println(result1);
}
```

#### PoJo结构的响应
在这个示例中，我们展示了如何使用 LangChain4j 提取人物信息，包括基本信息和带属性描述的信息。

```java
@Test
void TestPoJoStructResponse() {
    // 1. 获取人物信息
    CharacterAnalysis characterAnalysis = AiServices.create(CharacterAnalysis.class, chatModel);
    Person person = characterAnalysis.extractPersonFrom("李明，男，28岁，1996年3月15日出生。他是一名软件工程师，喜欢读书和跑步。");
    System.out.println(person.toString());

    // 不带属性描述的
    List<Person> peoples = characterAnalysis.extractPersonsFrom("在一个宁静的小镇上，李明是一位每天清晨都在公园里跑步的年轻人。他28岁，喜欢在阳光刚刚升起时享受清新的空气。1996年3月15日出生的李明总是第一个到达跑道。他的好友张华，经常和他一起晨跑。张华是个热情的27岁女孩，出生于1997年7月22日，作为一名老师，她热爱绘画，并且总是带着微笑与学生互动。\n" +
            "\n" +
            "周末时，他们两人会去镇上的咖啡馆，那里是他们的朋友王磊工作的地方。王磊30岁，1994年12月10日出生，是一名出色的咖啡师，总能为每个顾客调制出最美味的咖啡。每次李明和张华来访，王磊都会准备特别的咖啡款待他们，并一起聊聊最近的趣事。\n" +
            "\n" +
            "在一次闲聊中，李明提到他最近参加了一场马拉松比赛，而张华则分享了她在课堂上与孩子们的互动故事。王磊则谈起了他最近学习的新咖啡调制技巧。这些小镇上的简单而温暖的时光，让三人的友谊更加深厚。他们在彼此的陪伴中，享受着生活的美好。");
    System.out.println(peoples.size());
    System.out.println(peoples.toString());

    // 带属性描述的
    Persons peoples1 = characterAnalysis.extractPersonWithDescriptionsFrom("在一个宁静的小镇上，李明是一位每天清晨都在公园里跑步的年轻人。他28岁，喜欢在阳光刚刚升起时享受清新的空气。1996年3月15日出生的李明总是第一个到达跑道。他的好友张华，经常和他一起晨跑。张华是个热情的27岁女孩，出生于1997年7月22日，作为一名老师，她热爱绘画，并且总是带着微笑与学生互动。\n" +
            "\n" +
            "周末时，他们两人会去镇上的咖啡馆，那里是他们的朋友王磊工作的地方。王磊30岁，1994年12月10日出生，是一名出色的咖啡师，总能为每个顾客调制出最美味的咖啡。每次李明和张华来访，王磊都会准备特别的咖啡款待他们，并一起聊聊最近的趣事。\n" +
            "\n" +
            "在一次闲聊中，李明提到他最近参加了一场马拉松比赛，而张华则分享了她在课堂上与孩子们的互动故事。王磊则谈起了他最近学习的新咖啡调制技巧。这些小镇上的简单而温暖的时光，让三人的友谊更加深厚。他们在彼此的陪伴中，享受着生活的美好。");
    System.out.println(peoples1.getPersons().size());
    System.out.println(peoples1.getPersons().toString());
}
```

### 工具函数的调用 🔧

以下是如何使用 LangChain4j 中的工具函数的一个案例。

#### 工具的使用

在这个示例中，我们展示了如何使用 LangChain4j 中的工具类 `Calculator` 和接口 `Mathematician` 来处理数学问题和 Java 代码生成。

```java
@Test
void TestTools() {
    Mathematician mathematician = AiServices.builder(Mathematician.class)
            .tools(new Calculator()).chatLanguageModel(chatModel).build();
    String chat = mathematician.chat("“hello”和“world”的字母数之和的平方根是多少？");
    System.out.println(chat);
    String chat1 = mathematician.chat("帮我写一个java的冒泡排序");
    System.out.println(chat1);
}
```

`Mathematician` 接口的定义如下：
```java
public interface Mathematician {
    @SystemMessage("你是一个专业的数学家，只会回答数学问题！")
    String chat(String text);
}
```

`Calculator` 工具类的定义如下：
```java
public class Calculator {
    @Tool("计算字符串的长度")
    int stringLength(String s) {
        System.out.println("字符串长度 s='" + s + "'");
        return s.length();
    }

    @Tool("计算两数之和")
    int add(int a, int b) {
        System.out.println("计算两数之和 a=" + a + ", b=" + b);
        return a + b;
    }

    @Tool("计算数字的平方根")
    double sqrt(int x) {
        System.out.println("计算数字的平方根 x=" + x);
        return Math.sqrt(x);
    }
}
```

### Embedding模型使用与向量存储 📊
以下是如何配置和使用 LangChain4j 的 Embedding 模型和向量存储的案例。

#### 向量模型的配置
在这个示例中，我们展示了如何加载 EmbeddingModel。以 OpenAI 的 Embedding 模型。

```java
private EmbeddingModel loadEmbeddingModel(EmbeddingModelProperties properties) {
    // TODO 模型参数校验
    if (properties.getProviderName().equals("openai")) {
        return OpenAiEmbeddingModel.builder()
                .apiKey(properties.getApiKey())
                .baseUrl(properties.getBaseUrl())
                .build();
    }
    log.warn("EmbeddingModel:{}","未有匹配的供应商");
    return null;
}
```

#### 向量数据库（以 Elasticsearch 为例）的配置
在这个示例中，我们展示了如何配置 ElasticsearchEmbeddingStore 以用于向量存储。

```java
@Bean
@Lazy
public ElasticsearchEmbeddingStore getDefaultElasticsearchEmbeddingStore(EmbeddingStoreProperties properties) {
    String elasticHost = properties.getElasticHost();
    int elasticPort = properties.getElasticPort();
    String url = String.format("%s:%d", elasticHost, elasticPort);
    log.info("加载EmbeddingStore:{}", url);
    return ElasticsearchEmbeddingStore.builder()
            .serverUrl(url)
            .userName(properties.getElasticUsername())
            .password(properties.getElasticPassword())
            .indexName(properties.getIndexName())
            .dimension(1536)
            .build();
}
```
#### 向量模型的使用
在这个示例中，我们展示了如何使用 EmbeddingModel 生成文本的向量表示。

```java
@Test
void TestEmbedding() {
    Response<Embedding> response = embeddingModel.embed("帮我写一个java的冒泡排序");
    System.out.println(response.content().vectorAsList().size());
    System.out.println(response.content().vectorAsList());
}
```

#### 向量数据库的使用（以 Elasticsearch 为例）
在这个示例中，我们展示了如何将生成的向量存储到 ElasticsearchEmbeddingStore 中。

```java
@Test
void TestEmbeddingStore() {
    // 1. 存储
    Response<Embedding> emd = embeddingModel.embed("帮我写一个java的冒泡排序");
    Embedding content = emd.content();
    embeddingStore.add(String.valueOf(1), content);
}
```

#### 文本向量检索
在这个示例中，我们展示了如何使用 EmbeddingModel 和 ElasticsearchEmbeddingStore 进行文本向量的检索。

```java
@Test
void TestSearchEmbeddingStore() {
    // 1. 文本1
    TextSegment segment1 = TextSegment.from("人工智能（AI）是计算机科学的一个分支，它涉及模拟人类智能的机器。" +
            "这些机器可以执行诸如理解自然语言、识别人脸、玩游戏以及进行复杂计算等任务。" +
            "近年来，人工智能技术在各个领域得到了广泛应用，包括医疗、金融、教育和交通等。" +
            "尤其是深度学习和神经网络的发展，使得人工智能在图像识别和语音识别等方面取得了显著进展。");
    // 文本转向量
    Embedding content1 = embeddingModel.embed(segment1).content();
    embeddingStore.add(content1, segment1);

    // 2. 文本2
    TextSegment segment2 = TextSegment.from("机器学习是人工智能的一个重要分支，通过数据和算法来训练计算机模型，以便它们能够自动完成特定任务。" +
            "机器学习算法可以分为监督学习、无监督学习和强化学习三种类型。监督学习使用标注数据进行训练，常用于分类和回归问题；" +
            "无监督学习使用未标注数据，常用于聚类和降维；强化学习通过与环境的交互来学习策略，广泛应用于机器人控制和游戏AI。");
    // 文本转向量
    Embedding content2 = embeddingModel.embed(segment2).content();
    embeddingStore.add(content2, segment2);

    Embedding queryEmbedding = embeddingModel.embed("什么是人工智能？").content();
    List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(queryEmbedding, 1);
    EmbeddingMatch<TextSegment> embeddingMatch = relevant.get(0);
    System.out.println(embeddingMatch.score());
    System.out.println(embeddingMatch.embedded().text());

    Embedding queryEmbedding1 = embeddingModel.embed("人工智能在医疗领域的应用有哪些？").content();
    List<EmbeddingMatch<TextSegment>> relevant1 = embeddingStore.findRelevant(queryEmbedding1, 1);
    EmbeddingMatch<TextSegment> embeddingMatch1 = relevant1.get(0);
    System.out.println(embeddingMatch1.score());
    System.out.println(embeddingMatch1.embedded().text());

    Embedding queryEmbedding2 = embeddingModel.embed("机器学习的三种类型是什么？").content();
    List<EmbeddingMatch<TextSegment>> relevant2 = embeddingStore.findRelevant(queryEmbedding2, 1);
    EmbeddingMatch<TextSegment> embeddingMatch2 = relevant2.get(0);
    System.out.println(embeddingMatch2.score());
    System.out.println(embeddingMatch2.embedded().text());

    Embedding queryEmbedding3 = embeddingModel.embed("监督学习和无监督学习的区别是什么？").content();
    List<EmbeddingMatch<TextSegment>> relevant3 = embeddingStore.findRelevant(queryEmbedding3, 1);
    EmbeddingMatch<TextSegment> embeddingMatch3 = relevant3.get(0);
    System.out.println(embeddingMatch3.score());
    System.out.println(embeddingMatch3.embedded().text());
}
```

### RAG搜索（知识库搭建）
以下是如何使用 LangChain4j 进行 RAG（Retrieval-Augmented Generation）搜索，通过文本向量训练来搭建知识库的案例。

#### RAG 文档向量训练
在这个示例中，我们展示了如何使用 LangChain4j 进行 PDF 文档的向量训练，以实现知识库的构建和搜索。

```java
@Test
void TestRAGDocumentEmbeddingTrain() {
    // 1. PDF 文本训练
    Path path = toPath("/example-files/阿里巴巴泰山版java开发手册.pdf");
    System.out.println("文档路径:" + path);
    Document document = loadDocument(path, new ApacheTikaDocumentParser());
    // System.out.println("文档内容:" + document);

    // 2. 文本分割
    DocumentSplitter splitter = DocumentSplitters.recursive(100, 0);
    List<TextSegment> split = splitter.split(document);
    System.out.println("文档分割块大小:" + split.size());

    // 3. 进行文本训练
    EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
            .documentSplitter(splitter)
            .embeddingModel(embeddingModel)
            .embeddingStore(embeddingStore)
            .build();
    ingestor.ingest(document);
}
```
`ApacheTikaDocumentParser` 是一个能识别和解析多种文档格式的工具，例如 PDF、DOC、TXT 等。

#### RAG 代码审查
在这个示例中，我们展示了如何使用 RAG 文本搜索功能进行代码审查。

```java
@Test
void TestRAGDocumentSearch() {
    ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
            .embeddingStore(embeddingStore)
            .embeddingModel(embeddingModel)
            .maxResults(5) // 最大搜索结果
            .minScore(0.9) // 最小匹配得分
            .build();
    CodeReview codeReviewAiServices = AiServices.builder(CodeReview.class)
            .chatLanguageModel(chatModel)
            .contentRetriever(contentRetriever)
            .build();
    String chat = codeReviewAiServices.chat("float a = 1.0f - 0.9f;\n" +
            "float b = 0.9f - 0.8f;\n" +
            "if (a == b) {\n" +
            "// 预期进入此代码快，执行其它业务逻辑\n" +
            "// 但事实上 a==b 的结果为 false\n" +
            "}\n" +
            "Float x = Float.valueOf(a);\n" +
            "Float y = Float.valueOf(b);\n" +
            "if (x.equals(y)) {\n" +
            "// 预期进入此代码快，执行其它业务逻辑\n" +
            "// 但事实上 equals 的结果为 false\n" +
            "}");
    System.out.println(chat);
}
```
#### RAG 原理
索引阶段的简化图
![ragsave.png](image%2Fragsave.png)

检索阶段的简化图
![ragsave.png](image%2Fragsave.png)


### Agent代理 🤖
请参考测试案例`TestCustomerSupportAgent`的使用


## 扩展
### 支持模型列表 ([Docs](https://docs.langchain4j.dev/category/integrations))
| Provider                                                                                           | Native Image | [Sync Completion](https://docs.langchain4j.dev/category/language-models) | [Streaming Completion](https://docs.langchain4j.dev/integrations/language-models/response-streaming) | [Embedding](https://docs.langchain4j.dev/category/embedding-models) | [Image Generation](https://docs.langchain4j.dev/category/image-models) | [Scoring](https://docs.langchain4j.dev/category/scoring-models) | [Function Calling](https://docs.langchain4j.dev/tutorials/tools) |
|----------------------------------------------------------------------------------------------------|--------------|--------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------|------------------------------------------------------------------------|-----------------------------------------------------------------|------------------------------------------------------------------|
| [OpenAI](https://docs.langchain4j.dev/integrations/language-models/open-ai)                        | ✅            | ✅                                                                        | ✅                                                                                                    | ✅                                                                   | ✅                                                                      |                                                                 | ✅                                                                |                                                                                                
| [Azure OpenAI](https://docs.langchain4j.dev/integrations/language-models/azure-open-ai)            |              | ✅                                                                        | ✅                                                                                                    | ✅                                                                   | ✅                                                                      |                                                                 | ✅                                                                | 
| [Hugging Face](https://docs.langchain4j.dev/integrations/language-models/hugging-face)             |              | ✅                                                                        |                                                                                                      | ✅                                                                   |                                                                        |                                                                 |                                                                  |  |
| [Amazon Bedrock](https://docs.langchain4j.dev/integrations/language-models/amazon-bedrock)         |              | ✅                                                                        | ✅                                                                                                     | ✅                                                                   | ✅                                                                      |                                                                 |                                                                  |
| [Google Vertex AI Gemini](https://docs.langchain4j.dev/integrations/language-models/google-gemini) |              | ✅                                                                        | ✅                                                                                                    |                                                                     | ✅                                                                      |                                                                 | ✅                                                                |
| [Google Vertex AI](https://docs.langchain4j.dev/integrations/language-models/google-palm)          | ✅            | ✅                                                                        |                                                                                                      | ✅                                                                   | ✅                                                                      |                                                                 |                                                                  |
| [Mistral AI](https://docs.langchain4j.dev/integrations/language-models/mistral-ai)                 |              | ✅                                                                        | ✅                                                                                                    | ✅                                                                   |                                                                        |                                                                 | ✅                                                                |
| [DashScope](https://docs.langchain4j.dev/integrations/language-models/dashscope)                   |              | ✅                                                                        | ✅                                                                                                    | ✅                                                                   |                                                                        |                                                                 |                                                                  |
| [LocalAI](https://docs.langchain4j.dev/integrations/language-models/local-ai)                      |              | ✅                                                                        | ✅                                                                                                    | ✅                                                                   |                                                                        |                                                                 | ✅                                                                |
| [Ollama](https://docs.langchain4j.dev/integrations/language-models/ollama)                         |              | ✅                                                                        | ✅                                                                                                    | ✅                                                                   |                                                                        |                                                                 |                                                                  |
| [Cohere](https://docs.langchain4j.dev/integrations/reranking-models/cohere)                        |              |                                                                          |                                                                                                      |                                                                     |                                                                        | ✅                                                               |                                                                  |
| [Qianfan](https://docs.langchain4j.dev/integrations/language-models/qianfan)                       |              | ✅                                                                        | ✅                                                                                                    | ✅                                                                   |                                                                        |                                                                 | ✅                                                                |
| [ChatGLM](https://docs.langchain4j.dev/integrations/language-models/chatglm)                       |              | ✅                                                                        |                                                                                                      |                                                                     |                                                                        |                                                                 |                                                                  |
| [Nomic](https://docs.langchain4j.dev/integrations/language-models/nomic)                           |              |                                                                          |                                                                                                      | ✅                                                                   |                                                                        |                                                                 |                                                                  |
| [Anthropic](https://docs.langchain4j.dev/integrations/language-models/anthropic)                   | ✅            | ✅                                                                        | ✅                                                                                                    |                                                                     |                                                                        |                                                                 | ✅                                                                |
| [Zhipu AI](https://docs.langchain4j.dev/integrations/language-models/zhipu-ai)                     |              | ✅                                                                        | ✅                                                                                                    | ✅                                                                   |                                                                        |                                                                 | ✅                                                                |

### [免费的openai的APIkey使用](https://github.com/chatanywhere/GPT_API_free)

