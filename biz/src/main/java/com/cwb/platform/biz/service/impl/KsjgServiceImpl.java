package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizKsJgMapper;
import com.cwb.platform.biz.model.BizKsJg;
import com.cwb.platform.biz.service.KsjgService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 考试结果记录表
 * Created by Administrator on 2018/6/19.
 */
@Service
public class KsjgServiceImpl extends BaseServiceImpl<BizKsJg,String> implements KsjgService{
    @Autowired
    private BizKsJgMapper entityMapper;

    @Override
    protected Mapper<BizKsJg> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizKsJg.class;
    }
}
