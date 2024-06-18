package com.hailiang.langchain4jdemo.response;

import com.hailiang.langchain4jdemo.pojo.Person;
import com.hailiang.langchain4jdemo.pojo.Persons;
import dev.langchain4j.service.UserMessage;

import java.util.List;

public interface CharacterAnalysis {
    @UserMessage("从 {{it}} 这段话里获取人物信息!")
    Person extractPersonFrom(String text);

    @UserMessage("从 {{it}} 这段内容里分析出所有人物的姓名,年龄，性别,生日。")
    List<Person> extractPersonsFrom(String text);

    @UserMessage("从 {{it}} 这段内容里分析一共有几位人物，并且分别分析出他们的姓名，年龄，性别，生日")
    Persons extractPersonWithDescriptionsFrom(String text);
}
