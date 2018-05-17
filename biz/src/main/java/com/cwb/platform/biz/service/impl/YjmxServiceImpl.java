package com.cwb.platform.biz.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizYjmxMapper;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.YjmxService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class YjmxServiceImpl extends BaseServiceImpl<BizYjmx,java.lang.String> implements YjmxService{
    @Autowired
    private BizYjmxMapper entityMapper;

    @Override
    protected Mapper<BizYjmx> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizYjmx.class;
    }

   
}
