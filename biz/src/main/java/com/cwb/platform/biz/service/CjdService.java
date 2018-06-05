package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizCjd;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.Map;

public interface CjdService extends BaseService<BizCjd, String> {

    ApiResponse<String> giveMark(BizCjd bizCjd);

    ApiResponse<Map<String,Object>> getUserMessage(String xyid);
}
