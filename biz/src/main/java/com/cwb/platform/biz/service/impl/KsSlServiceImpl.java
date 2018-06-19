package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizKsSlMapper;
import com.cwb.platform.biz.model.BizKsSl;
import com.cwb.platform.biz.service.KsSlService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 学员考试受理信息表
 * Created by Administrator on 2018/6/19.
 */
@Service
public class KsSlServiceImpl extends BaseServiceImpl<BizKsSl,String> implements KsSlService {
    @Autowired
    private BizKsSlMapper entityMapper;

    @Override
    protected Mapper<BizKsSl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizKsSl.class;
    }
}
