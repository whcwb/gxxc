package com.cwb.platform.biz.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizHdMapper;
import com.cwb.platform.biz.model.BizHd;
import com.cwb.platform.biz.service.HdService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class HdServiceImpl extends BaseServiceImpl<BizHd,java.lang.String> implements HdService{
    @Autowired
    private BizHdMapper entityMapper;

    @Override
    protected Mapper<BizHd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizHd.class;
    }

   
}
