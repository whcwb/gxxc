package com.cwb.platform.biz.service;

import com.cwb.platform.biz.model.BizSubSchool;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

public interface SubSchoolService extends BaseService<BizSubSchool,String> {
    ApiResponse<String> getOpenid(String phone);
}
