package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizCpMapper;
import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.biz.service.CpService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class CpServiceImpl extends BaseServiceImpl<BizCp,String> implements CpService {

    @Autowired
    private BizCpMapper entityMapper;

    @Override
    protected Mapper<BizCp> getBaseMapper() {
        return entityMapper;
    }


    public ApiResponse<BizCp> getCpTyetList(String cpType){

        BizCp bizCp=entityMapper.getCpTyetList(cpType);
        return ApiResponse.success(bizCp);
    }

}
