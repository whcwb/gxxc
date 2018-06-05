package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizYhk;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.List;

public interface YhkService extends BaseService<BizYhk,String>{

    List<BizYhk> getList(BizYhk entity);

    ApiResponse<String> addUserCreditCard(BizYhk entity);
}
