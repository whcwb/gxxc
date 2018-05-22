package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizZh;
import com.cwb.platform.sys.base.BaseService;

import java.util.List;

public interface ZhService extends BaseService<BizZh,java.lang.String>{

    boolean userAccountUpdate(List<String> userId);
}
