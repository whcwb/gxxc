package com.cwb.platform.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.biz.service.TxService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;

@RestController
@RequestMapping("/api/tx")
public class TxController extends BaseController<BizTx,java.lang.String>{
    @Autowired
    private TxService service;

    @Override
    protected BaseService<BizTx, java.lang.String> getBaseService() {
        return service;
    }
}
