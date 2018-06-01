package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizYhkMapper;
import com.cwb.platform.biz.model.BizYhk;
import com.cwb.platform.biz.service.YhkService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class YhkServiceImpl extends BaseServiceImpl<BizYhk,String> implements YhkService {
    @Autowired
    private BizYhkMapper entityMapper;

    @Override
    protected Mapper<BizYhk> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizYhk.class;
    }

   
}
