package com.hailiang.langchain4jdemo.pojo;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class PersonWithDescription {
    /**
     * 姓名
     */
    @Description("故事人物的姓名")
    private String name;
    /**
     * 年龄
     */
    @Description("故事人物的年龄")
    private String age;
    /**
     * 性别
     */
    @Description("故事人物的性别")
    private String gender;
    /**
     * 生日
     */
    @Description("故事人物的生日")
    private String birthday;

    @Description("故事人物的职业")
    private String b;
}
