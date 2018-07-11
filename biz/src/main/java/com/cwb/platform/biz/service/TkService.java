package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizTk;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;


public interface TkService extends BaseService<BizTk,String>{


    ApiResponse<String> saveAddTk(BizTk bizTk, BizPtyh ptyh);

    ApiResponse<String> updateTkType(BizTk entity);
}
