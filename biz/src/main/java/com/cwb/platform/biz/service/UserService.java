package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService extends BaseService<BizUser,java.lang.String>{



    void updateJlId(List<String> list, String jlId,String jlType);

    List<String> getYhIds(List<String> ids);

    ApiResponse<PageInfo<BizUser>> getStudentList(String yhid, String xyZt, Page<BizUser> page);
}
