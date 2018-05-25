package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

public interface OrderService extends BaseService<BizOrder,java.lang.String>{
    ApiResponse<String> updateOrderPayTpye(BizOrder l);
}
