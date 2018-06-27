package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;

public interface JlService extends BaseService<BizJl,String>{


    ApiResponse<String> updateYhRz(BizPtyh obd);

    ApiResponse<String> updateEntity(BizJl entity);
}
