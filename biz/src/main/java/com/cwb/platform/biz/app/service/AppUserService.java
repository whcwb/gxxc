package com.cwb.platform.biz.app.service;


import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;

import java.util.List;

public interface AppUserService extends BaseService<BizUser,String>{

    ApiResponse<List<BizUser>> myTeam(String grade, String yhlx, String sfjf, String yhXm, Page<BizUser> page);
}
