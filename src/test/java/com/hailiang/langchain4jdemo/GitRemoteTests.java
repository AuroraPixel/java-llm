package com.hailiang.langchain4jdemo;

import com.hailiang.langchain4jdemo.agent.CodeReviewAgent;
import com.hailiang.langchain4jdemo.pojo.gitlab.CommentRequest;
import com.hailiang.langchain4jdemo.pojo.gitlab.CommitInfo;
import com.hailiang.langchain4jdemo.pojo.gitlab.MergeRequestInfo;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.DiffDetail;
import com.hailiang.langchain4jdemo.remote.GitLabRemote;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@SpringBootTest
class GitRemoteTests {
    @Autowired
    private GitLabRemote gitLabRemote;
    @Autowired
    private CodeReviewAgent codeReviewAgent;

    @Autowired
    private ChatLanguageModel model;

    @Test
    void TestGitLabRemoteCompare(){
        CommitInfo compare = gitLabRemote.compare("915","7041330bb734a35697fa91c7e722337bbe530bc2", "45642963166431b3fb9aa35135ad311768e8fb81");
//        System.out.println(compare.getDiffs().get(7).getDiff());
//        System.out.println("变更前");
//        System.out.println(compare.getDiffs().get(7).getBeforeDiff());
//        System.out.println("变更后");
//        System.out.println(compare.getDiffs().get(7).getAfterDiff());
//        System.out.println("在线网址");
//        System.out.println(compare.getWebUrl());
        System.out.println(compare.getDiffs().get(7).getBeforeAndAfterDiff());
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
        List<DiffDetail> mergeRequestDiff = gitLabRemote.getMergeRequestDiff(915,8);
        System.out.println(mergeRequestDiff);
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
        List<DiffDetail> commitDiff = gitLabRemote.getCommitDiff(915, "fec3d403f2cd1c54606ce8e85c9243a75c9284a2");
        System.out.println(commitDiff.get(0).getBeforeDiff());
        System.out.println(commitDiff.get(0).getAfterDiff());
    }


    @Test
    void TestGetCommitDifWithGpt(){
        List<DiffDetail> commitDiff = gitLabRemote.getCommitDiff(915, "d82f691cd75e4ffb452b106b4bf229f0c9e7243f");
        List<String> codeList = commitDiff.stream().map(diffDetail -> diffDetail.getBeforeAndAfterDiff()).collect(Collectors.toList());
        System.out.println(codeReviewAgent.codeReview(codeList));
    }


    @Test
    void TestCommentMergeRequest(){
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setBody("测试11");
        commentRequest.setId(915);
        commentRequest.setMergeRequestId(9);
        gitLabRemote.CommentMergeRequest(915,8,commentRequest);
    }

    @Test
    void TestErrorCode(){
        List<String> result = new ArrayList<>();
        System.out.println(result.get(1));
    }


    @Test
    void TestLongToken(){
        List<DiffDetail> diffs = gitLabRemote.getCommitDiff(553, "40bdab89a1c45d6c41f1101954b541cb828dd68b");
        StringBuilder diffString = new StringBuilder();
        diffs = diffs.stream().limit(20).skip(15).collect(Collectors.toList());
        for (DiffDetail diffDetail : diffs) {
            diffString.append(diffDetail.getDiffAndCodeName());
            diffString.append("\n");
        }
        OpenAiChatModel aiChatModel = (OpenAiChatModel) model;
        int i = aiChatModel.estimateTokenCount(diffString.toString());

        System.out.println(i);
    }


    @Test
    void TestNewPrompt(){
        //List<DiffDetail> diffs = gitLabRemote.getCommitDiff(553, "c03920553e4b8083fdebc739f5d1e38b6b1bb212");
        List<DiffDetail> diffs = gitLabRemote.getCommitDiff(915, "da6305e728d57df8cefa4d59354eff8813a7322b");
        List<String> codeList = diffs.stream().map(diffDetail -> diffDetail.getBeforeAndAfterDiff()).collect(Collectors.toList());
        System.out.println(codeList);
        System.out.println(diffs.size());
        System.out.println("开始代码审查");
        System.out.println(codeReviewAgent.codeReview(codeList));
    }

    void TestNewFunction(List<String> args){
        //List<DiffDetail> diffs = gitLabRemote.getCommitDiff(553, "c03920553e4b8083fdebc739f5d1e38b6b1bb212");
        List<DiffDetail> diffs = gitLabRemote.getCommitDiff(915, "da6305e728d57df8cefa4d59354eff8813a7322b");
        List<String> codeList = diffs.stream().map(diffDetail -> diffDetail.getBeforeAndAfterDiff()).collect(Collectors.toList());
        System.out.println(codeList);
        System.out.println(diffs.size());
        System.out.println("开始代码审查");
        System.out.println(codeReviewAgent.codeReview(codeList));
    }
}
