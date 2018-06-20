package com.cwb.platform.biz.service.impl;

import com.alipay.api.domain.SettleInfo;
import com.cwb.platform.biz.mapper.BizExamPlaceMapper;
import com.cwb.platform.biz.model.BizExamPlace;
import com.cwb.platform.biz.service.ExamPlaceService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * auther chenwei
 * create at 2018/6/19
 */
@Service
public class ExamServiceImpl extends BaseServiceImpl<BizExamPlace,String> implements ExamPlaceService {
    @Autowired
    private BizExamPlaceMapper examPlaceMapper;
    @Override
    protected Mapper<BizExamPlace> getBaseMapper() {
        return examPlaceMapper;
    }

    @Override
    public ApiResponse<String> validAndSave(BizExamPlace entity){
        entity.setId(genId());
        entity.setCjsj(DateUtils.getNowTime());
        examPlaceMapper.insertSelective(entity);
        return ApiResponse.success();
    }
}
