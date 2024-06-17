package com.hailiang.langchain4jdemo.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExternalApi {
    /**
     * 请求路径
     * @return
     */
    String path();

    /**
     * 默认请求方法
     * @return
     */
    String method() default "GET";

    /**
     * 客户度名
     * @return
     */
    String client();
}
