package com.cwb.platform.biz.service;

import com.cwb.platform.biz.model.BizKsSl;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * 学员考试受理信息表
 * Created by Administrator on 2018/6/19.
 */
public interface KsSlService extends BaseService<BizKsSl,String> {
    ApiResponse<Map<String,BizKsSl>> getHandleStatus(String yhId);
}
