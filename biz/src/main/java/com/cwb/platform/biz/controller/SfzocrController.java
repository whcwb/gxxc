package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.service.SfzocrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sfzocr")
//public class SfzocrController extends BaseController<BizSfzocr,java.lang.String>{
public class SfzocrController{
    @Autowired
    private SfzocrService service;

//    @Override
//    protected BaseService<BizSfzocr, java.lang.String> getBaseService() {
//        return service;
//    }
}
