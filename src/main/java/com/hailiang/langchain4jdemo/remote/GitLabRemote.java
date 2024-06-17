package com.hailiang.langchain4jdemo.remote;



import com.hailiang.langchain4jdemo.annotations.ExternalApi;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * gitlab远程调用接口
 */
@Service
public class GitLabRemote {


    /**
     * 比较分支、标签或提交
     * @param from
     * @param to
     * @return
     */
    @ExternalApi(path = "/compare", method = "GET", client = "gitClient")
    public String compare(@RequestParam("from") String from, @RequestParam("from")String to) {
        return null;
    }

}
