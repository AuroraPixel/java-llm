package com.hailiang.langchain4jdemo.tools;

import dev.langchain4j.agent.tool.Tool;

public class Calculator {
    @Tool("计算字符串的长度")
    int stringLength(String s) {
        System.out.println("字符串长度 s='" + s + "'");
        return s.length();
    }

    @Tool("计算两数之和")
    int add(int a, int b) {
        System.out.println("计算两数之和 a=" + a + ", b=" + b);
        return a + b;
    }

    @Tool("计算数字的平方根")
    double sqrt(int x) {
        System.out.println("计算数字的平方根 x=" + x);
        return Math.sqrt(x);
    }
}
