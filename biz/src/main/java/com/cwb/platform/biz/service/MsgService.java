package com.cwb.platform.biz.service;

import com.cwb.platform.biz.model.BizMsg;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;

public interface MsgService extends BaseService<BizMsg,String> {

    ApiResponse<String> getUserMsg(int pageNum, int pageSize);

    ApiResponse<String> reply(String content);

    ApiResponse<String> getUserList(int pageNum, int pageSize,String type);

    ApiResponse<String> getUserMsgs(int pageNum, int pageSize, String userId);

    ApiResponse<String> replyUser(String userId, String content);
}
