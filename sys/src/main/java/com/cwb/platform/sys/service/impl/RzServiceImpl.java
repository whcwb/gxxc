package com.cwb.platform.sys.service.impl;

import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.mapper.SysPtrzMapper;
import com.cwb.platform.sys.model.SysRz;
import com.cwb.platform.sys.service.RzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class RzServiceImpl extends BaseServiceImpl<SysRz,String> implements RzService {
    @Autowired
    private SysPtrzMapper entityMapper;

    @Override
    protected Mapper<SysRz> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return SysRz.class;
    }
    
    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        condition.setOrderByClause("czsj desc");
        return true;
    }
}
