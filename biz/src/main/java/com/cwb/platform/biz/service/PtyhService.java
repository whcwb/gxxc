package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizPtyh;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

public interface PtyhService extends BaseService<BizPtyh,java.lang.String>{

    /**
     *  更新用户是否锁定状态 0 否 1 是
     * @param bizPtyh
     * @return
     */
    ApiResponse<String> updateSfsd(BizPtyh bizPtyh);
    /**
     * 更新用户是否分配信息
     * @param bizPtyh
     * @return
     */
    ApiResponse<String> updateSffp(BizPtyh bizPtyh);
}
