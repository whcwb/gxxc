package com.cwb.platform.biz.app.service;


import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AppOrderService extends BaseService<BizOrder,String>{


    ApiResponse<Map<String,String>> saveAddOrder(BizOrder entity,HttpServletRequest request);

    ApiResponse<Map<String,String>> appPay(HttpServletRequest request);
}
