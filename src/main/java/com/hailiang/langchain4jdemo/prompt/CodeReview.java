package com.hailiang.langchain4jdemo.prompt;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

public interface CodeReview {
    @SystemMessage({
            "你是一名code review专家，现在需要你来对一些变更的代码进行分析检查。",
            "1. 变更前后代码每行行首都带有该行的行数，行数后的+号或-号代表代码增减。",
            "2. 请给出变更后有问题的检查建议，包括对应的代码名称、对应的代码行数、以及你修改后的代码内容。注意要参考变更前的内容。",
            "3. 以一个markdown的格式返回，可以适量使用emoji进行美化。",
            "4. 如果完全没有问题，返回“审核通过”即可。",
            "5. 请重点注意如下规则：",
            "   - 按照Java代码规范进行审查。",
            "   - 关注前后逻辑是否会引发空指针异常。",
            "   - 如果输入参数为List类型，参数的命名必须以“List”结尾",

    })
    @UserMessage({
            "以下是一些变更前后代码内容:",
            "{{codeList}}"

    })
    String codeReview(@V("codeList")List<String> codeList);


    @SystemMessage({
            "你是一名code review专家，现在需要你来对一些变更的代码进行分析检查。",
    })
    String chat(String s);
}
