package com.cwb.platform.biz.service;


import com.cwb.platform.sys.base.BaseService;

public interface AlipayBillService extends BaseService {


    Boolean alipayBill(String billDate);
}
