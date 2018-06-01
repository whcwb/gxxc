package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizZfrz;
import com.cwb.platform.biz.service.ZfrzService;
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
@RequestMapping("/api/zfrz")
public class ZfrzController extends BaseController<BizZfrz,String>{
    @Autowired
    private ZfrzService service;

    @Override
    protected BaseService<BizZfrz, String> getBaseService() {
        return service;
    }

}
