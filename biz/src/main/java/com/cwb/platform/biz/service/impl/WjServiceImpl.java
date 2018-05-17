package com.cwb.platform.biz.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizWjMapper;
import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class WjServiceImpl extends BaseServiceImpl<BizWj,java.lang.String> implements WjService{
    @Autowired
    private BizWjMapper entityMapper;

    @Override
    protected Mapper<BizWj> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizWj.class;
    }

   
}
