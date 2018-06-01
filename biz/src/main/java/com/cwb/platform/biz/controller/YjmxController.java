package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.YjmxService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/yjmx")
public class YjmxController extends QueryController<BizYjmx,String> {
//public class YjmxController{
    @Autowired
    private YjmxService service;

    @Override
    protected BaseService<BizYjmx, String> getBaseService() {
        return service;
    }
}
