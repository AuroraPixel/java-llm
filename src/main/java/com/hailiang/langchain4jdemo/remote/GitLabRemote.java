package com.hailiang.langchain4jdemo.remote;



import com.hailiang.langchain4jdemo.annotations.ExternalApi;
import com.hailiang.langchain4jdemo.pojo.gitlab.CommitInfo;
import com.hailiang.langchain4jdemo.pojo.gitlab.MergeRequestInfo;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.DiffDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public CommitInfo compare(@PathVariable("projectId") String projectId, @RequestParam("from") String from, @RequestParam("to")String to) {
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
    public MergeRequestInfo getSingleMR(@PathVariable("projectId") String projectId, @PathVariable("mergeRequestId") String mergeRequestId) {
        return null;
    }

    @ExternalApi(path = "{projectId}/merge_requests/{mergeRequestId}/diffs", method = "GET", client = "gitClient")
    public List<DiffDetail> getMergeRequestDiff(@PathVariable("projectId") Integer projectId, @PathVariable("mergeRequestId") Integer mergeRequestId) {
        return null;
    }


    /**
     * 获取提交的差异
     * @return
     */
    @ExternalApi(path = "{projectId}/repository/commits/{sha}/diff", method = "GET", client = "gitClient")
    public List<DiffDetail> getCommitDiff(@PathVariable("projectId") Integer projectId, @PathVariable("sha") String sha){
        return null;
    }

}
