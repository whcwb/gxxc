package com.cwb.platform.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwb.platform.biz.model.BizHd;
import com.cwb.platform.biz.service.HdService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;

@RestController
@RequestMapping("/api/hd")
public class HdController extends BaseController<BizHd,java.lang.String>{
    @Autowired
    private HdService service;

    @Override
    protected BaseService<BizHd, java.lang.String> getBaseService() {
        return service;
    }
}
