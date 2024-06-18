package com.hailiang.langchain4jdemo.remote;



import com.hailiang.langchain4jdemo.annotations.ExternalApi;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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
    @ExternalApi(path = "{projectId}/repository/compare", method = "GET", client = "gitClient")
    public String compare(@PathVariable("projectId") String projectId, @RequestParam("from") String from, @RequestParam("to")String to) {
        return null;
    }

    /**
     * 比较分支、标签或提交
     * @param id
     * @return
     */
    @ExternalApi(path = "{projectId}/repository/commits", method = "GET", client = "gitClient")
    public String getCommits(@PathVariable("projectId") String projectId,@RequestParam("id") String id) {
        return null;
    }


    /**
     * 获取单个MR
     * @return
     */
    @ExternalApi(path = "{projectId}/merge_requests/{mergeRequestId}", method = "GET", client = "gitClient")
    public String getSingleMR(@PathVariable("projectId") String projectId,@PathVariable("mergeRequestId") String mergeRequestId) {
        return null;
    }

}
