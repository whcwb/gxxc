package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.biz.model.BizYhk;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;

public interface TxService extends BaseService<BizTx,java.lang.String>{
    /**
     * 更新审核状态
     * @param bizTx
     * @return
     */
    ApiResponse<String> updateShzt(BizTx bizTx);

    ApiResponse<String> updateTxzt(BizTx bizTx);

    ApiResponse<String> saveUserDraw(Double ttJe, String yhkid, BizYhk bizYhk, BizPtyh user);
}
