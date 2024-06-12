package com.hailiang.langchain4jdemo.response.pojo;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Persons {
    /**
     * 人物列表
     */
    @Description("人物列表")
    List<PersonWithDescription> persons;
}
