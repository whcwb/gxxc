package com.cwb.platform.biz.service;


import com.cwb.platform.biz.app.bean.StudentListModel;
import com.cwb.platform.biz.model.BizCjd;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface CjdService extends BaseService<BizCjd, String> {

    ApiResponse<String> giveMark(BizCjd bizCjd);

    ApiResponse<Map<String,Object>> getUserMessage(String xyid);

    ApiResponse<PageInfo<StudentListModel>> getBizCjbList(Page<StudentListModel> ptyhPage);
}
