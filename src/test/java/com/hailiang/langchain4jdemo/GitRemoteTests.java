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
        String compare = gitLabRemote.getCommits("915","45642963166431b3fb9aa35135ad311768e8fb81");
        System.out.println(compare);
    }


    @Test
    void TestGitLabRemoteSingleMR(){
        MergeRequestInfo compare = gitLabRemote.getSingleMR("915","4");
        System.out.println(compare);
    }


    @Test
    void TestGitLabRemoteMergeRequestDiff(){
        String compare = gitLabRemote.getMergeRequestDiff("915","4");
        System.out.println(compare);
    }


    @Test
    void TestGetDiffCode(){
        String diff = "@@ -2,6 +2,7 @@ package com.hailiang.langchain4jdemo;\n \n import com.hailiang.langchain4jdemo.pojo.gitlab.CommitInfo;\n import com.hailiang.langchain4jdemo.pojo.gitlab.MergeRequestInfo;\n+import com.hailiang.langchain4jdemo.pojo.gitlab.detail.DiffDetail;\n import com.hailiang.langchain4jdemo.remote.GitLabRemote;\n import org.junit.jupiter.api.Test;\n import org.springframework.beans.factory.annotation.Autowired;\n@@ -17,7 +18,13 @@ class GitRemoteTests {\n     @Test\n     void TestGitLabRemoteCompare(){\n         CommitInfo compare = gitLabRemote.compare(\"915\",\"5bf3349a67fc4743985be1feb2ccecf8ec5f792e\", \"1cc8ee2a9bd7147490cd5efcf62c4e4687ac7ea1\");\n-        System.out.println(compare);\n+        System.out.println(compare.getDiffs().get(compare.getDiffs().size()-1).getDiff());\n+        System.out.println(\"变更前\");\n+        System.out.println(compare.getDiffs().get(compare.getDiffs().size()-1).getBeforeDiff());\n+        System.out.println(\"变更后\");\n+        System.out.println(compare.getDiffs().get(compare.getDiffs().size()-1).getAfterDiff());\n+        System.out.println(\"在线网址\");\n+        System.out.println(compare.getWebUrl());\n     }\n \n     @Test\n@@ -32,4 +39,22 @@ class GitRemoteTests {\n         MergeRequestInfo compare = gitLabRemote.getSingleMR(\"915\",\"4\");\n         System.out.println(compare);\n     }\n+\n+\n+    @Test\n+    void TestGetDiffCode(){\n+        String diff = \"@@ -22,7 +22,7 @@ public @interface ExternalApi {\\\\n\\\" +\\n\" +\n+                \"                      \\\"     String method() default \\\\\\\"GET\\\\\\\";\\\\n\\\" +\\n\" +\n+                \"                      \\\" \\\\n\\\" +\\n\" +\n+                \"                      \\\"     /**\\\\n\\\" +\\n\" +\n+                \"                      \\\"-     * 客户度名\\\\n\\\" +\\n\" +\n+                \"                      \\\"+     * 客户端名\\\\n\\\" +\\n\" +\n+                \"                      \\\"      * @return\\\\n\\\" +\\n\" +\n+                \"                      \\\"      */\\\\n\\\" +\\n\" +\n+                \"                      \\\"     String client();\\\\n\";\n+        DiffDetail diffDetail = new DiffDetail();\n+        diffDetail.setDiff(diff);\n+        System.out.println(diffDetail.getAfterDiff());\n+        System.out.println(diffDetail.getBeforeDiff());\n+    }\n }\n";
        DiffDetail diffDetail = new DiffDetail();
        diffDetail.setDiff(diff);
        System.out.println(diffDetail.getAfterDiff());
        System.out.println(diffDetail.getBeforeDiff());
    }

    @Test
    void TestGetCommitDif(){
        String commitDiff = gitLabRemote.getCommitDiff("915", "45642963166431b3fb9aa35135ad311768e8fb81");
        System.out.println(commitDiff);
    }
}
