package com.hailiang.langchain4jdemo.prompt;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

public interface CodeReview {
    @SystemMessage({
            "1.你是一名code review专家，现在需要你来对一些变更的代码进行分析检查。",
            "2.变更前后代码每行行首都该行的行数，行数后的+号或-号代表代码增减。",
            "3.请给出变更后有问题的检查的建议,对应代码名称,对应的代码行数,对应你修改后代码内容。注意要参考变更前的内容。",
            "4.以一个markdown的格式返回,可以适量使用emoji进行美化",
            "5.如果完全没有问题，返回审核通过就行。",
            "6.请注意如下规则:",
            "按照Java代码规范进行审查。",
            "重点关注前后逻辑是否会引发空指针异常。",
    })
    @UserMessage({
            "以下是一些变更前后代码内容:",
            "{{codeList}}"

    })
    String codeReview(@V("codeList")List<String> codeList);
}
