package com.cwb.platform.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwb.platform.biz.model.BizZh;
import com.cwb.platform.biz.service.ZhService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;

@RestController
@RequestMapping("/api/zh")
public class ZhController extends BaseController<BizZh,java.lang.String>{
    @Autowired
    private ZhService service;

    @Override
    protected BaseService<BizZh, java.lang.String> getBaseService() {
        return service;
    }
}
