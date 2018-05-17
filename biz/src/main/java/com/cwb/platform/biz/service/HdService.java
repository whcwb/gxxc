package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizHd;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

public interface HdService extends BaseService<BizHd,java.lang.String>{

    ApiResponse<String> saveEntity(BizHd entity);

    ApiResponse<String> updateEntity(BizHd entity);
}
