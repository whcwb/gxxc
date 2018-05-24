package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.List;

public interface JobService extends BaseService<BizOrder,String> {

    List<BizOrder> orderFulfil();

    ApiResponse<String> updateOrderFulfilDispose(BizOrder l);
}
