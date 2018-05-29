package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizJlMapper;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.service.JlService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

public class JlServiceImpl extends BaseServiceImpl<BizJl,String> implements JlService {

    @Autowired
    private BizJlMapper entityMapper;

    @Override
    protected Mapper<BizJl> getBaseMapper() {
        return entityMapper;
    }


}
