package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.biz.model.BizYhk;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.List;

public interface TxService extends BaseService<BizTx,java.lang.String>{
    /**
     * 更新审核状态
     * @param bizTx
     * @return
     */
    ApiResponse<String> updateShzt(BizTx bizTx);

    ApiResponse<String> updateTxzt(BizTx bizTx);

    ApiResponse<String> wxEnterprisePayRealize(String orderId, String openID, int amount, String userName, String desc);

    ApiResponse<String> saveUserDraw(Double ttJe, String yhkid, BizYhk bizYhk, BizPtyh user, String ttfs);

    ApiResponse<List<String>> batchImport(String filePath);

    ApiResponse<String> wxEnterprisePay(BizTx bizTx);
}
