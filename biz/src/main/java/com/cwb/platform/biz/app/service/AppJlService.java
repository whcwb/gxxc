package com.cwb.platform.biz.app.service;


import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

public interface AppJlService extends BaseService<BizJl,String>{

    ApiResponse<String> getMyStudent(String jz, String xm, int pageNum, int pageSize);
}
