package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizKsSl;
import com.cwb.platform.biz.service.KsSlService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学员考试受理信息表
 * Created by Administrator on 2018/6/19.
 */
@RestController
@RequestMapping("/api/kssl")
public class KsSlController extends QueryController<BizKsSl,String> {

    @Autowired
    private KsSlService service;

    @Override
    protected BaseService<BizKsSl, String> getBaseService() {
        return service;
    }
}
