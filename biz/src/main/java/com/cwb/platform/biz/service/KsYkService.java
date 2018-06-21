package com.cwb.platform.biz.service;

import com.cwb.platform.biz.model.BizKsYk;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.Map;

/**
 * 学员考试约考表
 * Created by Administrator on 2018/6/19.
 */
public interface KsYkService extends BaseService<BizKsYk,String> {
    ApiResponse<Map<String,BizKsYk>> getUserExamInfo(String yhId);
}
