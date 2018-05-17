package com.cwb.platform.sys.service;

import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.SysFw;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.List;

public interface FwService extends BaseService<SysFw, String> {
    ApiResponse<String> saveEntity(SysFw entity);
    ApiResponse<String> updateEntity(SysFw entity);

    /**
     * 设置机构服务
     * @param orgCode 机构代码
     * @param serviceCodes 服务代码
     * @return 执行结果
     */
    ApiResponse<String> setOrgService(String orgCode,List<String> serviceCodes);

    /**
     * 查询机构拥有的功能列表
     * @param jgdm 机构代码
     * @return 功能列表
     */
    List<SysFw> findByJgdm(String jgdm);

//    ApiResponse<String> removeIdsWithCheck(String[] ids);
}
