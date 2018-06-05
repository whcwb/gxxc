package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizSfzocr;
import com.cwb.platform.biz.service.SfzocrService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sfzocr")
public class SfzocrController extends QueryController<BizSfzocr,String> {
//public class SfzocrController{
    @Autowired
    private SfzocrService service;

    @Override
    protected BaseService<BizSfzocr, String> getBaseService() {
        return service;
    }
}
