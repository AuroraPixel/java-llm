package com.hailiang.langchain4jdemo.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hailiang.langchain4jdemo.pojo.gitlab.WebHookRequest;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.ObjectAttributesDetail;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.ProjectDetail;
import com.hailiang.langchain4jdemo.remote.GitLabRemote;
import com.hailiang.langchain4jdemo.service.CodeReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CodeReviewServiceImpl implements CodeReviewService {
    @Autowired
    private GitLabRemote gitLabRemote;
    @Override
    public void review(WebHookRequest request) {
        if(ObjectUtil.isNull(request)){
            return;
        }
        //获取项目信息
        ProjectDetail project = request.getProject();
        if(ObjectUtil.isNull(project)){
            return;
        }
        //项目id
        Integer projectId = project.getId();
        if(ObjectUtil.isNull(projectId)){
            return;
        }
        //请求方式
        String objectKind = request.getObjectKind();
        if(StrUtil.isEmpty(objectKind)){
            return;
        }
        //工程对象属性
        ObjectAttributesDetail objectAttributes = request.getObjectAttributes();
        if(ObjectUtil.isNull(objectAttributes)){
            return;
        }
        //当前操作方式
        String action = objectAttributes.getAction();
        if(StrUtil.isEmpty(action)){
            return;
        }
        //MergeRequest的id
        Integer mergeRequestId = objectAttributes.getIid();
        if(ObjectUtil.isNull(mergeRequestId)){
            return;
        }

        //1.首先判断请求方式是否为 merge_request
        if(!objectKind.equals("merge_request")){
            return;
        }
        //2.在判断当前操作方式是否 open 和 update
        if(!action.equals("open")&&!action.equals("update")){
            return;
        }
        //3.如果是open进行merge的全量码捞取
        if(action.equals("open")){
            //获取MergeRequest的信息
            gitLabRemote.getMergeRequestDiff(projectId,mergeRequestId);
        }

        //4.如果是update进行merge的增量代码捞取

    }
}
