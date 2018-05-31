package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.service.WjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wj")
//public class WjController extends BaseController<BizWj,java.lang.String>{
public class WjController{
    @Autowired
    private WjService service;

//    @Override
//    protected BaseService<BizWj, java.lang.String> getBaseService() {
//        return service;
//    }
}
