package com.cwb.platform.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;

@RestController
@RequestMapping("/api/wj")
public class WjController extends BaseController<BizWj,java.lang.String>{
    @Autowired
    private WjService service;

    @Override
    protected BaseService<BizWj, java.lang.String> getBaseService() {
        return service;
    }
}
