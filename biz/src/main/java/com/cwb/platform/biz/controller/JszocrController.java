package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizJszocr;
import com.cwb.platform.biz.service.JszocrService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jszocr")
//public class JszocrController {
public class JszocrController extends QueryController<BizJszocr,String> {
    @Autowired
    private JszocrService service;

    @Override
    protected BaseService<BizJszocr, String> getBaseService() {
        return service;
    }
}
