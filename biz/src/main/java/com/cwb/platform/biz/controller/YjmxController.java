package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.service.YjmxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/yjmx")
//public class YjmxController extends BaseController<BizYjmx,java.lang.String>{
public class YjmxController{
    @Autowired
    private YjmxService service;

//    @Override
//    protected BaseService<BizYjmx, java.lang.String> getBaseService() {
//        return service;
//    }
}
