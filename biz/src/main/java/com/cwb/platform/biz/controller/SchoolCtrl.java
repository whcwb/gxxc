package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizDriversSchool;
import com.cwb.platform.biz.service.SchoolService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/school")
public class SchoolCtrl extends BaseController<BizDriversSchool,String> {
    @Autowired
    private SchoolService schoolService;
    @Override
    protected BaseService<BizDriversSchool, String> getBaseService() {
        return schoolService;
    }
}
