package com.cwb.platform.biz.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizZhMapper;
import com.cwb.platform.biz.model.BizZh;
import com.cwb.platform.biz.service.ZhService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class ZhServiceImpl extends BaseServiceImpl<BizZh,java.lang.String> implements ZhService{
    @Autowired
    private BizZhMapper entityMapper;

    @Override
    protected Mapper<BizZh> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizZh.class;
    }

   
}
