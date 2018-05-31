package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.service.ZhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zh")
//public class ZhController extends BaseController<BizZh,java.lang.String>{
public class ZhController{
    @Autowired
    private ZhService service;

//    @Override
//    protected BaseService<BizZh, java.lang.String> getBaseService() {
//        return service;
//    }
}
