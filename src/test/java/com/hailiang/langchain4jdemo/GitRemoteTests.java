package com.hailiang.langchain4jdemo;

import com.hailiang.langchain4jdemo.pojo.gitlab.CommitInfo;
import com.hailiang.langchain4jdemo.pojo.gitlab.MergeRequestInfo;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.DiffDetail;
import com.hailiang.langchain4jdemo.remote.GitLabRemote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@SpringBootTest
class GitRemoteTests {
    @Autowired
    private GitLabRemote gitLabRemote;

    @Test
    void TestGitLabRemoteCompare(){
        CommitInfo compare = gitLabRemote.compare("915","7041330bb734a35697fa91c7e722337bbe530bc2", "45642963166431b3fb9aa35135ad311768e8fb81");
        System.out.println(compare.getDiffs().get(7).getDiff());
        System.out.println("变更前");
        System.out.println(compare.getDiffs().get(7).getBeforeDiff());
        System.out.println("变更后");
        System.out.println(compare.getDiffs().get(7).getAfterDiff());
        System.out.println("在线网址");
        System.out.println(compare.getWebUrl());
    }

    @Test
    void TestGitLabRemoteCommits(){
        String compare = gitLabRemote.getCommits("915","66ca4bb6bc89f0f3ca205758bc5043cb1c3a4eaf");
        System.out.println(compare);
    }


    @Test
    void TestGitLabRemoteSingleMR(){
        MergeRequestInfo compare = gitLabRemote.getSingleMR("915","6");
        System.out.println(compare);
    }


    @Test
    void TestGitLabRemoteMergeRequestDiff(){
        String compare = gitLabRemote.getMergeRequestDiff("915","5");
        System.out.println(compare);
    }


    @Test
    void TestGetDiffCode(){
        String diff = "";
        DiffDetail diffDetail = new DiffDetail();
        diffDetail.setDiff(diff);
        System.out.println(diffDetail.getAfterDiff());
        System.out.println(diffDetail.getBeforeDiff());
    }

    @Test
    void TestGetCommitDif(){
        List<DiffDetail> commitDiff = gitLabRemote.getCommitDiff("915", "fec3d403f2cd1c54606ce8e85c9243a75c9284a2");
        System.out.println(commitDiff.get(1).getBeforeDiff());
        System.out.println(commitDiff.get(1).getAfterDiff());
    }
}
