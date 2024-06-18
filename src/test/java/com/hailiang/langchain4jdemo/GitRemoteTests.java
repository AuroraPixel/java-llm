package com.hailiang.langchain4jdemo;

import com.hailiang.langchain4jdemo.pojo.gitlab.CommitInfo;
import com.hailiang.langchain4jdemo.pojo.gitlab.MergeRequestInfo;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.DiffDetail;
import com.hailiang.langchain4jdemo.remote.GitLabRemote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@SpringBootTest
class GitRemoteTests {
    @Autowired
    private GitLabRemote gitLabRemote;

    @Test
    void TestGitLabRemoteCompare(){
        CommitInfo compare = gitLabRemote.compare("915","5bf3349a67fc4743985be1feb2ccecf8ec5f792e", "1cc8ee2a9bd7147490cd5efcf62c4e4687ac7ea1");
        System.out.println(compare.getDiffs().get(compare.getDiffs().size()-1).getDiff());
        System.out.println("变更前");
        System.out.println(compare.getDiffs().get(compare.getDiffs().size()-1).getBeforeDiff());
        System.out.println("变更后");
        System.out.println(compare.getDiffs().get(compare.getDiffs().size()-1).getAfterDiff());
        System.out.println("在线网址");
        System.out.println(compare.getWebUrl());
    }

    @Test
    void TestGitLabRemoteCommits(){
        String compare = gitLabRemote.getCommits("915","7041330bb734a35697fa91c7e722337bbe530bc2");
        System.out.println(compare);
    }


    @Test
    void TestGitLabRemoteSingleMR(){
        MergeRequestInfo compare = gitLabRemote.getSingleMR("915","4");
        System.out.println(compare);
    }


    @Test
    void TestGetDiffCode(){
        String diff = "@@ -22,7 +22,7 @@ public @interface ExternalApi {\\n\" +\n" +
                "                      \"     String method() default \\\"GET\\\";\\n\" +\n" +
                "                      \" \\n\" +\n" +
                "                      \"     /**\\n\" +\n" +
                "                      \"-     * 客户度名\\n\" +\n" +
                "                      \"+     * 客户端名\\n\" +\n" +
                "                      \"      * @return\\n\" +\n" +
                "                      \"      */\\n\" +\n" +
                "                      \"     String client();\\n";
        DiffDetail diffDetail = new DiffDetail();
        diffDetail.setDiff(diff);
        System.out.println(diffDetail.getAfterDiff());
        System.out.println(diffDetail.getBeforeDiff());
    }
}
