package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizSubSchoolMapper;
import com.cwb.platform.biz.model.BizSubSchool;
import com.cwb.platform.biz.service.SubSchoolService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

public class SubSchoolServiceImpl extends BaseServiceImpl<BizSubSchool,String> implements SubSchoolService {

    @Autowired
    private BizSubSchoolMapper baseMapper;

    @Override
    protected Mapper<BizSubSchool> getBaseMapper() {
        return baseMapper;
    }




}
