package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizDriversSchoolMapper;
import com.cwb.platform.biz.model.BizDriversSchool;
import com.cwb.platform.biz.service.SchoolService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class SchoolServiceImpl extends BaseServiceImpl<BizDriversSchool,String> implements SchoolService {
    @Autowired
    private BizDriversSchoolMapper schoolMapper;
    @Override
    protected Mapper<BizDriversSchool> getBaseMapper() {
        return schoolMapper;
    }

    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        condition.setOrderByClause("create_time desc");
        return true;
    }

    @Override
    public ApiResponse<String> validAndSave(BizDriversSchool school){
        school.setSchoolCode(genId());
        school.setCreateTime(DateUtils.getNowTime());

        schoolMapper.insertSelective(school);
        return ApiResponse.success();
    }
}
