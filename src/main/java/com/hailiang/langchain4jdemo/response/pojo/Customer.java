package com.hailiang.langchain4jdemo.response.pojo;

import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    /**
     * 名
     */
    @Description("名")
    private String name;
    /**
     * 姓
     */
    @Description("姓")
    private String surname;
}
