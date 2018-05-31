package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.service.JszocrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jszocr")
public class JszocrController {
//public class JszocrController extends BaseController<BizJszocr,String>{
    @Autowired
    private JszocrService service;

//    @Override
//    protected BaseService<BizJszocr, java.lang.String> getBaseService() {
//        return service;
//    }
}
