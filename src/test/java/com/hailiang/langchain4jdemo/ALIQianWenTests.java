package com.hailiang.langchain4jdemo;


import cn.hutool.json.JSONUtil;
import com.hailiang.langchain4jdemo.pojo.Persons;
import com.hailiang.langchain4jdemo.tools.Calculator;
import com.hailiang.langchain4jdemo.tools.WeatherTools;
import dev.langchain4j.agent.tool.*;
import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.ServiceOutputParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.util.*;

import static dev.langchain4j.agent.tool.ToolSpecifications.toolSpecificationFrom;

@SpringBootTest
public class ALIQianWenTests {


    /**
     * 加载阿里语言模型
     */
    @Test
    void LoadQianWenChatModelTest(){
        OpenAiChatModel chatModel = OpenAiChatModel.builder().baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey("sk-1513783e8ea84bc083e94216ed06a9a7")
                .modelName("qwen-long")
                .build();
        String generate = chatModel.generate("你是谁?");
        System.out.println(generate);
    }


    /**
     * 聊天对话使用
     */
    @Test
    void ChatWithMemoryTest(){
        OpenAiChatModel chatModel = OpenAiChatModel.builder().baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey("sk-1513783e8ea84bc083e94216ed06a9a7")
                .modelName("qwen-long")
                .build();
        List<ChatMessage> chatMessageList = new ArrayList<>();
        chatMessageList.add(new SystemMessage("你是海亮研究的HaiLiang大模型，并且是一个数学专家，只能回答数学相关的问题!"));
        chatMessageList.add(new UserMessage("你是谁?"));
        chatMessageList.add(new AiMessage("我是HaiLiang大模型。"));
        chatMessageList.add(new UserMessage("我刚刚提问了什么，并且你的回答是什么?"));
        Response<AiMessage> generate = chatModel.generate(chatMessageList);
        System.out.println(generate.content());
    }

    /**
     * 结构化输出
     */
    @Test
    void StructResponseTest(){
        OpenAiChatModel chatModel = OpenAiChatModel.builder().baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey("sk-1513783e8ea84bc083e94216ed06a9a7")
                .modelName("qwen-long")
                .build();
        List<ChatMessage> chatMessageList = new ArrayList<>();

        //系统提示词
        chatMessageList.add(new SystemMessage("你是语言内容分析专家，能根据语言内容分析不同人物信息。"));
        chatMessageList.add(new UserMessage("在一个宁静的小镇上，李明是一位每天清晨都在公园里跑步的年轻人。他28岁，喜欢在阳光刚刚升起时享受清新的空气。1996年3月15日出生的李明总是第一个到达跑道。他的好友张华，经常和他一起晨跑。张华是个热情的27岁女孩，出生于1997年7月22日，作为一名老师，她热爱绘画，并且总是带着微笑与学生互动。\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"周末时，他们两人会去镇上的咖啡馆，那里是他们的朋友王磊工作的地方。王磊30岁，1994年12月10日出生，是一名出色的咖啡师，总能为每个顾客调制出最美味的咖啡。每次李明和张华来访，王磊都会准备特别的咖啡款待他们，并一起聊聊最近的趣事。\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"在一次闲聊中，李明提到他最近参加了一场马拉松比赛，而张华则分享了她在课堂上与孩子们的互动故事。王磊则谈起了他最近学习的新咖啡调制技巧。这些小镇上的简单而温暖的时光，让三人的友谊更加深厚。他们在彼此的陪伴中，享受着生活的美好。"));

        //输出结构体
        String structPrompt = ServiceOutputParser.outputFormatInstructions(Persons.class);
        System.out.println("输出结构体要提示:"+structPrompt);
        chatMessageList.add(new UserMessage("The output must be in JSON format, and any non-JSON content is strictly prohibited. Only output { ... } and avoid using markdown format such as \"```json......```\"."));
        chatMessageList.add(new UserMessage(structPrompt));

        //json转为结构体
        Response<AiMessage> generate = chatModel.generate(chatMessageList);
        Persons persons = JSONUtil.toBean(generate.content().text(), Persons.class);
        System.out.println(persons);
    }


    @Test
    void UseToolsTest() throws Exception{
        //加载阿里语言模型
        ChatLanguageModel chatModel = OpenAiChatModel.builder().baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey("sk-1513783e8ea84bc083e94216ed06a9a7")
                .modelName("qwen-plus")
                .build();

        //工具转为提示词描述
        WeatherTools tools = new WeatherTools();
        List<ToolSpecification> toolSpecifications = ToolSpecifications.toolSpecificationsFrom(tools);
        System.out.println("工具提示词描述:"+toolSpecifications.toString());

        //查询
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new SystemMessage("你是天气预报专家，你可以使用工具查询具体城市的天气状况，进行会答，回答必须是中文。"));
        UserMessage userMessage = new UserMessage("上海明天天气怎么样?");
        chatMessages.add(userMessage);

        //调用大模型
        AiMessage aiMessage = chatModel.generate(chatMessages, toolSpecifications).content();
        List<ToolExecutionRequest> toolExecutionRequests = aiMessage.toolExecutionRequests();
        toolExecutionRequests.forEach(toolExecutionRequest -> {
            System.out.println("Function name: " + toolExecutionRequest.name());
            System.out.println("Function args:" + toolExecutionRequest.arguments());
        });

        //工具注册
        Map<String,ToolExecutor> toolExecutors = new HashMap<>();
        for (Method method : tools.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Tool.class)) {
                ToolSpecification toolSpecification = toolSpecificationFrom(method);
                toolExecutors.put(toolSpecification.name(), new DefaultToolExecutor(tools, method));
            }
        }

        //工具的调用
        for (ToolExecutionRequest toolExecutionRequest : toolExecutionRequests) {
            ToolExecutor toolExecutor = toolExecutors.get(toolExecutionRequest.name());
            System.out.println("开始调用工具: " + toolExecutionRequest.name());
            String result = toolExecutor.execute(toolExecutionRequest, UUID.randomUUID().toString());
            ToolExecutionResultMessage toolExecutionResultMessages = ToolExecutionResultMessage.from(toolExecutionRequest, result);
            chatMessages.add(toolExecutionResultMessages);
        }

        //最终生成
        AiMessage finalResponse = chatModel.generate(chatMessages).content();
        System.out.println(finalResponse.text());

    }

    @Test
    void RAGWithEmbedding(){

    }
}
