package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizYhk;
import com.cwb.platform.biz.service.YhkService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 银行卡管理
 *
 */
@RestController
@RequestMapping("/api/yhk")
public class YhkController extends BaseController<BizYhk,String>{
    @Autowired
    private YhkService service;

    @Override
    protected BaseService<BizYhk, String> getBaseService() {
        return service;
    }

}
