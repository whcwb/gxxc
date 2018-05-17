package com.cwb.platform.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwb.platform.biz.model.BizSfzocr;
import com.cwb.platform.biz.service.SfzocrService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;

@RestController
@RequestMapping("/api/sfzocr")
public class SfzocrController extends BaseController<BizSfzocr,java.lang.String>{
    @Autowired
    private SfzocrService service;

    @Override
    protected BaseService<BizSfzocr, java.lang.String> getBaseService() {
        return service;
    }
}
