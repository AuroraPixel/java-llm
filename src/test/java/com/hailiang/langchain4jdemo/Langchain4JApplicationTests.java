package com.hailiang.langchain4jdemo;

import cn.hutool.core.collection.ListUtil;
import com.hailiang.langchain4jdemo.prompt.CookingAssistant;
import com.hailiang.langchain4jdemo.response.CharacterAnalysis;
import com.hailiang.langchain4jdemo.response.InputReview;
import com.hailiang.langchain4jdemo.response.NumberAndDateExtractor;
import com.hailiang.langchain4jdemo.response.SentimentAnalyzer;
import com.hailiang.langchain4jdemo.response.enu.SentimentEnum;
import com.hailiang.langchain4jdemo.response.pojo.Person;
import com.hailiang.langchain4jdemo.response.pojo.PersonWithDescription;
import com.hailiang.langchain4jdemo.response.pojo.Persons;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Langchain4JApplicationTests {
    @Autowired
    private ChatLanguageModel chatModel;
    @Autowired
    private StreamingChatLanguageModel streamingChatModel;


    /**
     * 普通简单使用
     */
    @Test
    void TestChatModel() {
        String response = chatModel.generate("你是谁?");
        System.out.println(response);
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
            Thread.sleep(10000);
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
        variables.put("language","英语");
        Prompt prompt = promptTemplate.apply(variables);


        System.out.println(prompt.text());
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



}
