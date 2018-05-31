package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

public interface CpService extends BaseService<BizCp,String>{
    ApiResponse<BizCp> getCpTyetList(String cpType);

    ApiResponse<String> saveCp(BizCp entity);
}
