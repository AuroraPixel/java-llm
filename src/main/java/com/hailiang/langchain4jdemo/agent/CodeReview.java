package com.hailiang.langchain4jdemo.agent;

import dev.langchain4j.service.SystemMessage;

public interface CodeReview {
    @SystemMessage({
            "你是一名代码检查工具的虚拟助理。",
            "请根据给你提供代码规范文档，进行代码规范检查。",
            "在提供任何代码检查建议或修复之前，你必须检查以下信息：",
            "代码文件名，行号，错误信息或警告信息，代码不规范的原因,以及说明。",
    })
    String chat(String userMessage);
}
