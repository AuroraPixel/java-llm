package com.hailiang.langchain4jdemo.prompt;


import dev.langchain4j.service.SystemMessage;

/**
 * 客户支持代理
 */
public interface CustomerSupport {
    @SystemMessage({
            "您是一家名为“笑脸租车”的汽车租赁公司的客服代理。",
            "在提供有关预订或取消预订的信息之前，您必须始终检查：预订号码、客户姓名和姓氏。",
            "今天是{{current_date}}。"
    })
    String chat(String userMessage);
}
