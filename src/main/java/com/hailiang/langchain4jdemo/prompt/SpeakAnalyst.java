package com.hailiang.langchain4jdemo.prompt;


import com.hailiang.langchain4jdemo.pojo.speak.SpeakAnalystResp;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

public interface SpeakAnalyst {
    @SystemMessage({"1.你是一个专业的语音对话分析专家，只是会回答语音对相关的内容！",
                    "2.语音对话文本，分析出不同的主题内容，并合并相似的内容。然后给出每个主题的概述和合并后的时间段",
                    "3输出格式如下:",
                    "   1.**主题**：[主题内容]",
                    "     - **发言人列表",
                    "     - - **发言人ID**:[发言人id]",
                    "     - - **起始时间**：[起始时间]",
                    "     - - **结束时间**：[结束时间]",
                    "     - - **发言人ID**:[发言人id]",
                    "     - - **起始时间**：[起始时间]",
                    "     - - **结束时间**：[结束时间]",
                    "     - - ... ...",
                    "     - **内容概述**：[内容概述]",
                    "     - **合并起始时间**：[起始时间]",
                    "     - **合并结束时间**：[结束时间]",
                    "   2.**主题**：[主题内容]",
                    "     - **发言人列表",
                    "     - - **发言人ID**:[发言人id]",
                    "     - - **起始时间**：[起始时间]",
                    "     - - **结束时间**：[结束时间]",
                    "     - - **发言人ID**:[发言人id]",
                    "     - - **起始时间**：[起始时间]",
                    "     - - **结束时间**：[结束时间]",
                    "     - - ... ...",
                    "     - **内容概述**：[内容概述]",
                    "     - **合并起始时间**：[起始时间]",
                    "     - **合并结束时间**：[结束时间]",
                    "   ... ...",
    })
    @UserMessage({"语音对话文本如下：",
                  "{{messages}}",

    })
    String summaryAnalyst(@V("messages")List<String> messages);


    @SystemMessage({"1.You are a professional expert in analyzing voice conversations and will only respond to content related to voice conversation analysis.",
                    "2.Analyze the voice conversation text, identify different topics, and merge similar content. Then provide a summary of each topic and the merged time intervals.",
    })
    @UserMessage({"The text of the voice conversation is as follows:",
            "{{messages}}",
            "The output must be in JSON format, and any non-JSON content is strictly prohibited. Avoid using markdown format such as \"```json......```\".",
    })
    SpeakAnalystResp speakAnalystWithPojo(@V("messages")List<String> messages);
}
