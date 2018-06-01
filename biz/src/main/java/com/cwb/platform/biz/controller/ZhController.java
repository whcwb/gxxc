package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizZh;
import com.cwb.platform.biz.service.ZhService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zh")
public class ZhController extends QueryController<BizZh,String> {
//public class ZhController{
    @Autowired
    private ZhService service;

    @Override
    protected BaseService<BizZh, String> getBaseService() {
        return service;
    }
}
