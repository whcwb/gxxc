package com.cwb.platform.biz.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizSfzocrMapper;
import com.cwb.platform.biz.model.BizSfzocr;
import com.cwb.platform.biz.service.SfzocrService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class SfzocrServiceImpl extends BaseServiceImpl<BizSfzocr,java.lang.String> implements SfzocrService{
    @Autowired
    private BizSfzocrMapper entityMapper;

    @Override
    protected Mapper<BizSfzocr> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizSfzocr.class;
    }

   
}
