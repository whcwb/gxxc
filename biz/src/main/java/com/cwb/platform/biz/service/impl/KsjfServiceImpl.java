package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizKsJfMapper;
import com.cwb.platform.biz.model.BizKsJf;
import com.cwb.platform.biz.service.KsjfService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 学员考试缴费记录表
 * Created by Administrator on 2018/6/19.
 */
@Service
public class KsjfServiceImpl extends BaseServiceImpl<BizKsJf,String> implements KsjfService {
    @Autowired
    private BizKsJfMapper entityMapper;

    @Override
    protected Mapper<BizKsJf> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizKsJf.class;
    }

}
