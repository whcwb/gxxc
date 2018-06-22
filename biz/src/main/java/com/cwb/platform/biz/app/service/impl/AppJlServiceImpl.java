package com.cwb.platform.biz.app.service.impl;


import com.cwb.platform.biz.app.service.AppJlService;
import com.cwb.platform.biz.mapper.BizJlMapper;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class AppJlServiceImpl extends BaseServiceImpl<BizJl,String> implements AppJlService {
    @Autowired
    private BizJlMapper entityMapper;


    @Override
    protected Mapper<BizJl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizYjmx.class;
    }

    /**
     * 分页补充
     * @param condition
     * @return
     */
    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        return true;
    }


   
}
