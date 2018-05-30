package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;

import java.util.List;
import java.util.Map;

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

    BizPtyh findByIdSelect(String userid);

    ApiResponse<String> userEnroll (BizPtyh entity);

    ApiResponse<String> mdfPwd(String userId, String oldPwd, String newPwd);

    ApiResponse<Map<String,Object>> wxlogin(String openId);

    ApiResponse<String> updateUserInfo(BizPtyh entity);

    ApiResponse<String> updateUserReal(BizPtyh entity);

    ApiResponse<String> updateYhRz(BizPtyh bizPtyh);

    BizPtyh getUserInvitationCode(String id);
    /**
     * 用户申请教练
     */
    ApiResponse<String> updatelx(BizJl bizJl);

    ApiResponse<List<BizPtyh>> getCoaches(String name, String phone, String area, int pageNum, int pageSize);

    ApiResponse<List<String>> assignStudents(String yhId, String jlId);

    ApiResponse<List<BizPtyh>> getBizPtyhList();

    boolean sendSMS(String tel, int type,  String identifyingCode);
    /**
     * 短信验证
     * @param tel    手机号码
     * @param type     redis key值
     *@param identifyingCode    验证码
     * @return
     */
    ApiResponse<String> validateSms(String tel, String identifyingCode,String type);


    ApiResponse<String> resetPwd(String tel, String code, String newPwd);
}
