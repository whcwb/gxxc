package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizCjd;
import com.cwb.platform.biz.service.CjdService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 学生成绩单
 *
 */
@RestController
@RequestMapping("/api/cjd")
public class CjdController extends BaseController<BizCjd,String>{
    @Autowired
    private CjdService service;

    @Override
    protected BaseService<BizCjd, String> getBaseService() {
        return service;
    }

}
