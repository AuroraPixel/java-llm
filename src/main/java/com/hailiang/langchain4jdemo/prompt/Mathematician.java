package com.hailiang.langchain4jdemo.prompt;

import dev.langchain4j.service.SystemMessage;

public interface Mathematician {
    @SystemMessage("你是一个专业的数学家，只是会回答数学问题！")
    String chat(String text);
}
