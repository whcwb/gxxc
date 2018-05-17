package com.cwb.platform.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwb.platform.biz.model.BizPtyh;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;

@RestController
@RequestMapping("/api/ptyh")
public class PtyhController extends BaseController<BizPtyh,java.lang.String>{
    @Autowired
    private PtyhService service;

    @Override
    protected BaseService<BizPtyh, java.lang.String> getBaseService() {
        return service;
    }
}
