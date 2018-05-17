package com.cwb.platform.biz.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizJszocrMapper;
import com.cwb.platform.biz.model.BizJszocr;
import com.cwb.platform.biz.service.JszocrService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class JszocrServiceImpl extends BaseServiceImpl<BizJszocr,java.lang.String> implements JszocrService{
    @Autowired
    private BizJszocrMapper entityMapper;

    @Override
    protected Mapper<BizJszocr> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizJszocr.class;
    }

   
}
