package com.cwb.platform.biz.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.model.BizPtyh;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class PtyhServiceImpl extends BaseServiceImpl<BizPtyh,java.lang.String> implements PtyhService{
    @Autowired
    private BizPtyhMapper entityMapper;

    @Override
    protected Mapper<BizPtyh> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizPtyh.class;
    }

   
}
