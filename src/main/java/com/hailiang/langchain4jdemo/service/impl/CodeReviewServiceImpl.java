package com.hailiang.langchain4jdemo.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.hailiang.langchain4jdemo.pojo.gitlab.WebHookRequest;
import com.hailiang.langchain4jdemo.pojo.gitlab.detail.ObjectAttributesDetail;
import com.hailiang.langchain4jdemo.service.CodeReviewService;
import org.springframework.stereotype.Service;


@Service
public class CodeReviewServiceImpl implements CodeReviewService {
    @Override
    public void review(WebHookRequest request) {
        if(ObjectUtil.isNull(request)){
            return;
        }
        //请求方式
        String objectKind = request.getObjectKind();
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

        //1.首先判断请求方式是否为 merge_request
        if(StrUtil.isEmpty(objectKind)||!objectKind.equals("merge_request")){
            return;
        }
        //2.在判断当前操作方式

    }
}
