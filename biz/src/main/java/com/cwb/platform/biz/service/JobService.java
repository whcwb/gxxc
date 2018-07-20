package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface JobService extends BaseService<BizOrder,String> {

    List<BizOrder> orderFulfil();

    ApiResponse<String> updateOrderFulfilDispose(BizOrder l);

//    List<String> billContrast(WxPayBillResult billResult,String billDate);

    ApiResponse<String> wxDownloadBill(String billDate);

    ApiResponse<String> balanceBillAccount(String billDate,Boolean handcraft);
}
