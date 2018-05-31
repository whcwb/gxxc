package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizCjdMapper;
import com.cwb.platform.biz.model.BizCjd;
import com.cwb.platform.biz.service.CjdService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class CjdServiceImpl extends BaseServiceImpl<BizCjd,String> implements CjdService {

    @Autowired
    private BizCjdMapper entityMapper;

    @Override
    protected Mapper<BizCjd> getBaseMapper() {
        return entityMapper;
    }


}
