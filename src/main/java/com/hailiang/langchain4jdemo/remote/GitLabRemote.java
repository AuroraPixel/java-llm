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
    @ExternalApi(path = "/repository/compare", method = "GET", client = "gitClient")
    public String compare(@RequestParam("from") String from, @RequestParam("to")String to) {
        return null;
    }

    /**
     * 比较分支、标签或提交
     * @param id
     * @return
     */
    @ExternalApi(path = "/repository/commits", method = "GET", client = "gitClient")
    public String commits(@RequestParam("id") String id) {
        return null;
    }

}
