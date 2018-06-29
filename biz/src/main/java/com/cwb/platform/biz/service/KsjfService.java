package com.cwb.platform.biz.service;

import com.cwb.platform.biz.model.BizKsJf;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/19.
 */
public interface KsjfService extends BaseService<BizKsJf,String> {
    ApiResponse<Map<String,String>> getPayInfo(String yhId);

    ApiResponse<List<BizPtyh>> waitPaymentList(Integer km);

    List<List<String>> export(Integer km);

    ApiResponse<String> batchImport(String filePath);
}
