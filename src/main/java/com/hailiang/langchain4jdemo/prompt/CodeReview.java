package com.hailiang.langchain4jdemo.prompt;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

public interface CodeReview {
    @SystemMessage({
            "你是一名code review专家，现在需要你来对一些变更的代码进行分析检查。",
            "变更前后代码每行行首都该行的行数，行数后的+号或-号代表代码增减。",
            "没有问题点请不要描述和展示。",
            "如果审查没有问题，可以进行少量语言描述通过许可就行。",
            "如果有问题请提出你的改进意见。",
            "请给出检查的建议和对应的代码行数，并以一个言语精简和markdown的格式返回,可以适量使用emoji进行美化。",
            "请注意如下规则:",
            "1.按照Java代码规范进行审查。",
            "2.要重点关注前后逻辑是否会引发空指针异常。",
    })
    @UserMessage({
            "以下是一些变更前后代码内容:",
            "{{}}"

    })
    String codeReview(@V("codeList")List<String> codeList);
}
