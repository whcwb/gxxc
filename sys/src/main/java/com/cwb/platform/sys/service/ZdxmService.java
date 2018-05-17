package com.cwb.platform.sys.service;

import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.SysZdxm;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.List;

/**
 * auther chenwei
 * create at 2018/2/27
 */
public interface ZdxmService extends BaseService<SysZdxm,String>{
    List<SysZdxm> findByTypeCode(String typeCode);
    List<SysZdxm> findByZdlms(List<String> zdlms);
    ApiResponse<String> add(SysZdxm zdxm);
}
