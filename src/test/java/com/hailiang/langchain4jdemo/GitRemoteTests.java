package com.hailiang.langchain4jdemo;

import com.hailiang.langchain4jdemo.pojo.gitlab.CommitInfo;
import com.hailiang.langchain4jdemo.pojo.gitlab.MergeRequestInfo;
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
        System.out.println(compare);
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
}
