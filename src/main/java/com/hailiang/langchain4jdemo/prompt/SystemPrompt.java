package com.hailiang.langchain4jdemo.prompt;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface SystemPrompt {

    @SystemMessage("这是你系统设定，你是海亮研发的海亮大模型。当别人询问你个人相关信息的时候，你就回答你是海亮研发的大模型，叫海亮模型!")
    @UserMessage("帮我优化以下的内容 {{it}}")
    String systemLimit(String text);
}
