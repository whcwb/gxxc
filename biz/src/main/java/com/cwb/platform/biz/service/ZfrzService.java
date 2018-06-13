package com.cwb.platform.biz.service;


import com.cwb.platform.biz.model.BizZfrz;
import com.cwb.platform.sys.base.BaseService;

public interface ZfrzService extends BaseService<BizZfrz,String>{

    void addObject(BizZfrz payLog);
}
