package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wj")
public class WjController extends QueryController<BizWj,String> {
//public class WjController{
    @Autowired
    private WjService service;

    @Override
    protected BaseService<BizWj, String> getBaseService() {
        return service;
    }
}
