package com.cwb.platform.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.YjmxService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;

@RestController
@RequestMapping("/api/yjmx")
public class YjmxController extends BaseController<BizYjmx,java.lang.String>{
    @Autowired
    private YjmxService service;

    @Override
    protected BaseService<BizYjmx, java.lang.String> getBaseService() {
        return service;
    }
}
