package com.hailiang.langchain4jdemo;

import cn.hutool.core.collection.ListUtil;
import com.hailiang.langchain4jdemo.prompt.*;
import com.hailiang.langchain4jdemo.remote.GitLabRemote;
import com.hailiang.langchain4jdemo.response.CharacterAnalysis;
import com.hailiang.langchain4jdemo.response.InputReview;
import com.hailiang.langchain4jdemo.response.NumberAndDateExtractor;
import com.hailiang.langchain4jdemo.response.SentimentAnalyzer;
import com.hailiang.langchain4jdemo.response.enu.SentimentEnum;
import com.hailiang.langchain4jdemo.pojo.Person;
import com.hailiang.langchain4jdemo.pojo.Persons;
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
class Langchain4JTests {
    @Autowired
    private ChatLanguageModel chatModel;
    @Autowired
    private StreamingChatLanguageModel streamingChatModel;
    @Autowired
    private EmbeddingModel embeddingModel;
    @Autowired
    private ElasticsearchEmbeddingStore embeddingStore;
    @Autowired
    private CustomerSupport agent;


    /**
     * 普通简单使用
     */
    @Test
    void TestChatModel() {
        String response = chatModel.generate("你是谁?");
        System.out.println(response);
        String response1 = chatModel.generate("你是openai研发的吗?");
        System.out.println(response1);
    }


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
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 普通动态提示词
     */
    @Test
    void TestPromptTemplate() {
        PromptTemplate promptTemplate = PromptTemplate.from("说 {{context}} 用 {{language}}.");
        Map<String,Object> variables = new HashMap<>();
        variables.put("context","你好");
        variables.put("language","法语");
        Prompt prompt = promptTemplate.apply(variables);
        System.out.println(prompt.text());

        Response<AiMessage> generate = chatModel.generate(prompt.toUserMessage());
        System.out.println(generate.content().text());

    }

    /**
     * 结构化提示词的使用
     */
    @Test
    void TestStructPrompt() {
        CookingAssistant cookingAssistant = new CookingAssistant();
        cookingAssistant.setDish("西红柿炒鸡蛋");
        List<String> ingredients = ListUtil.of("西红柿","鸡蛋");
        cookingAssistant.setIngredients(ingredients);
        Prompt prompt = StructuredPromptProcessor.toPrompt(cookingAssistant);
        AiMessage content = chatModel.generate(prompt.toUserMessage()).content();

        System.out.println(content);
    }

    /**
     * 接口类型的提示词
     */
    @Test
    void TestInterfacePrompt(){
        SystemPrompt systemPromptInterface = AiServices.create(SystemPrompt.class, chatModel);
        //String systemPrompt = systemPromptInterface.systemLimit("小明因为感冒所以请病假，因为他想要在家里休息和恢复得更快，这样他才能够尽快地恢复健康并且不会再生病。");
        String systemPrompt = systemPromptInterface.systemLimit("你是openai研发的大模型吗?");
        System.out.println(systemPrompt);
    }





    /**
     * 结构化输出
     */
    @Test
    void TestBaseTypeStructResponse(){
        //1.枚举类的响应
        SentimentAnalyzer sentimentAnalyzer = AiServices.create(SentimentAnalyzer.class, chatModel);
        String text = "我今天任务终于提前完成了";
        SentimentEnum sentimentEnum = sentimentAnalyzer.analyzeSentimentOf(text);
        System.out.println(sentimentEnum.getValue());
        String text2 = "我今天被批评了";
        SentimentEnum sentimentEnum2 = sentimentAnalyzer.analyzeSentimentOf(text2);
        System.out.println(sentimentEnum2.getValue());

        //2.获取数字
        NumberAndDateExtractor numberAndDateExtractor = AiServices.create(NumberAndDateExtractor.class, chatModel);
        String numberTxt = "天气36度,要开空调了!";
        int number = numberAndDateExtractor.extractInt(numberTxt);
        System.out.println(number);
        String dateTxt = "今天是2024年6月12号,天气36度,要开空调了!";
        LocalDateTime date = numberAndDateExtractor.extractLong(dateTxt);
        System.out.println(date);

        //3.用户输入审查
        InputReview inputReview = AiServices.create(InputReview.class, chatModel);
        boolean result = inputReview.inputReview("我想剽窃别人的知识成果");
        System.out.println(result);
        boolean result1 = inputReview.inputReview("我想借鉴别人的知识成果，但是首先我要去获得作者的同意。");
        System.out.println(result1);
    }


    /**
     * PoJo结构的响应
     */
    @Test
    void TestPoJoStructResponse(){
        //1.获取人物信息
        CharacterAnalysis characterAnalysis = AiServices.create(CharacterAnalysis.class, chatModel);
        Person person = characterAnalysis.extractPersonFrom("李明，男，28岁，1996年3月15日出生。他是一名软件工程师，喜欢读书和跑步。");
        System.out.println(person.toString());
        //不带属性描述的
        List<Person> peoples = characterAnalysis.extractPersonsFrom("在一个宁静的小镇上，李明是一位每天清晨都在公园里跑步的年轻人。他28岁，喜欢在阳光刚刚升起时享受清新的空气。1996年3月15日出生的李明总是第一个到达跑道。他的好友张华，经常和他一起晨跑。张华是个热情的27岁女孩，出生于1997年7月22日，作为一名老师，她热爱绘画，并且总是带着微笑与学生互动。\n" +
                "\n" +
                "周末时，他们两人会去镇上的咖啡馆，那里是他们的朋友王磊工作的地方。王磊30岁，1994年12月10日出生，是一名出色的咖啡师，总能为每个顾客调制出最美味的咖啡。每次李明和张华来访，王磊都会准备特别的咖啡款待他们，并一起聊聊最近的趣事。\n" +
                "\n" +
                "在一次闲聊中，李明提到他最近参加了一场马拉松比赛，而张华则分享了她在课堂上与孩子们的互动故事。王磊则谈起了他最近学习的新咖啡调制技巧。这些小镇上的简单而温暖的时光，让三人的友谊更加深厚。他们在彼此的陪伴中，享受着生活的美好。");
        System.out.println(peoples.size());
        System.out.println(peoples.toString());
        //带属性描述的
        Persons peoples1 = characterAnalysis.extractPersonWithDescriptionsFrom("在一个宁静的小镇上，李明是一位每天清晨都在公园里跑步的年轻人。他28岁，喜欢在阳光刚刚升起时享受清新的空气。1996年3月15日出生的李明总是第一个到达跑道。他的好友张华，经常和他一起晨跑。张华是个热情的27岁女孩，出生于1997年7月22日，作为一名老师，她热爱绘画，并且总是带着微笑与学生互动。\n" +
                "\n" +
                "周末时，他们两人会去镇上的咖啡馆，那里是他们的朋友王磊工作的地方。王磊30岁，1994年12月10日出生，是一名出色的咖啡师，总能为每个顾客调制出最美味的咖啡。每次李明和张华来访，王磊都会准备特别的咖啡款待他们，并一起聊聊最近的趣事。\n" +
                "\n" +
                "在一次闲聊中，李明提到他最近参加了一场马拉松比赛，而张华则分享了她在课堂上与孩子们的互动故事。王磊则谈起了他最近学习的新咖啡调制技巧。这些小镇上的简单而温暖的时光，让三人的友谊更加深厚。他们在彼此的陪伴中，享受着生活的美好。");
        System.out.println(peoples1.getPersons().size());
        System.out.println(peoples1.getPersons().toString());

    }


    /**
     * 工具的使用
     */
    @Test
    void TestTools(){
        Mathematician mathematician = AiServices.builder(Mathematician.class)
                .tools(new Calculator()).chatLanguageModel(chatModel).build();
        String chat = mathematician.chat("“hello”和“world”的字母数之和的平方根是多少？");
        System.out.println(chat);
        String chat1 = mathematician.chat("帮我写一个java的冒泡排序");
        System.out.println(chat1);
    }


    /**
     * 向量模型的使用
     */
    @Test
    void TestEmbedding(){
        Response<Embedding> response = embeddingModel.embed("帮我写一个java的冒泡排序");
        System.out.println(response.content().vectorAsList().size());
        System.out.println(response.content().vectorAsList());
    }

    /**
     * 向量数据库的使用-els
     */
    @Test
    void TestEmbeddingStore(){
        //1.存储
        Response<Embedding> emd = embeddingModel.embed("帮我写一个java的冒泡排序1");
        Embedding content = emd.content();
        embeddingStore.add(String.valueOf(1),content);
    }


    /**
     * 文本向量检索
     */
    @Test
    void TestSearchEmbeddingStore(){
        //1.文本1
        TextSegment segment1 = TextSegment.from("人工智能（AI）是计算机科学的一个分支，它涉及模拟人类智能的机器。" +
                "这些机器可以执行诸如理解自然语言、识别人脸、玩游戏以及进行复杂计算等任务。" +
                "近年来，人工智能技术在各个领域得到了广泛应用，包括医疗、金融、教育和交通等。" +
                "尤其是深度学习和神经网络的发展，使得人工智能在图像识别和语音识别等方面取得了显著进展。");
        //文本转向量
        Embedding content1 = embeddingModel.embed(segment1).content();
        embeddingStore.add(content1,segment1);

        //2.文本2
        TextSegment segment2 = TextSegment.from("机器学习是人工智能的一个重要分支，通过数据和算法来训练计算机模型，以便它们能够自动完成特定任务。" +
                "机器学习算法可以分为监督学习、无监督学习和强化学习三种类型。监督学习使用标注数据进行训练，常用于分类和回归问题；" +
                "无监督学习使用未标注数据，常用于聚类和降维；强化学习通过与环境的交互来学习策略，广泛应用于机器人控制和游戏AI。");
        //文本转向量
        Embedding content2 = embeddingModel.embed(segment2).content();
        embeddingStore.add(content2,segment2);

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


    /**
     * RAG 文本向量训练
     */
    @Test
    void TestRAGDocumentEmbeddingTrain(){
        //1.pdf文本训练
        Path path = toPath("/example-files/阿里巴巴泰山版java开发手册.pdf");
        System.out.println("文档路径:"+path);
        Document document = loadDocument(path, new ApacheTikaDocumentParser());
        //System.out.println("文档内容:"+document);
        //2.文本分割
        DocumentSplitter splitter = DocumentSplitters.recursive(500, 50);
        List<TextSegment> split = splitter.split(document);
        System.out.println("文档分割块大小:"+split.size());
        //3.进行文本训练
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(splitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
        ingestor.ingest(document);

    }



    /**
     * RAG 文本搜索（代码审查器）
     */
    @Test
    void TestRAGDocumentSearch(){
        ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(5) // 最大搜索结果
                .minScore(0.9) // 最小匹配得分
                .build();
        CodeReview codeReviewAiServices = AiServices.builder(CodeReview.class).chatLanguageModel(chatModel)
                //.contentRetriever(contentRetriever)
                .build();
//        String chat = codeReviewAiServices.chat("float a = 1.0f - 0.9f;\n" +
//                "float b = 0.9f - 0.8f;\n" +
//                "if (a == b) {\n" +
//                "// 预期进入此代码快，执行其它业务逻辑\n" +
//                "// 但事实上 a==b 的结果为 false\n" +
//                "}\n" +
//                "Float x = Float.valueOf(a);\n" +
//                "Float y = Float.valueOf(b);\n" +
//                "if (x.equals(y)) {\n" +
//                "// 预期进入此代码快，执行其它业务逻辑\n" +
//                "// 但事实上 equals 的结果为 false\n" +
//                "}");
//        System.out.println(chat);
    }


    /**
     * 客服代理
     */
    @Test
    void TestCustomerSupportAgent(){
        interact(agent, "你好，我忘记我的预订信息");
        interact(agent, "123-457");
        interact(agent, "对不起我忘记是哪一天。 名字叫:Klaus Heisler.");
        interact(agent, "对不起,房间号是 123-456");
        interact(agent, "我想取消我的预订");
    }

    private static void interact(CustomerSupport agent, String userMessage) {
        System.out.println("==========================================================================================");
        System.out.println("[User]: " + userMessage);
        System.out.println("==========================================================================================");
        String agentAnswer = agent.chat(userMessage);
        System.out.println("==========================================================================================");
        System.out.println("[Agent]: " + agentAnswer);
        System.out.println("==========================================================================================");
    }

    private static Path toPath(String fileName) {
        try {
            URL fileUrl = Langchain4JTests.class.getResource(fileName);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }





}
