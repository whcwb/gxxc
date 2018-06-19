package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizKsJf;
import com.cwb.platform.biz.service.KsjfService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学员考试缴费记录表
 * Created by Administrator on 2018/6/19.
 */
@RestController
@RequestMapping("/api/ksjf")
public class KsJfController extends QueryController<BizKsJf,String> {

    @Autowired
    private KsjfService service;

    @Override
    protected BaseService<BizKsJf, String> getBaseService() {
        return service;
    }
}
