package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.sys.base.BaseService;

import java.util.List;

public interface UserService extends BaseService<BizUser,java.lang.String>{



    void updateJlId(List<String> list, String jlId);

    List<String> getYhIds(List<String> ids);

}
