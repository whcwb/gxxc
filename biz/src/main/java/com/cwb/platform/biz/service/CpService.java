package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.List;

public interface CpService extends BaseService<BizCp,String>{
    ApiResponse<List<BizCp>> getCpTyetList();
    ApiResponse<String> saveCp(BizCp entity);


    ApiResponse<BizCp> getCpTyet(String cpType);

    ApiResponse<String> validateCSMS(String cpId, String code1, String code2);
}
