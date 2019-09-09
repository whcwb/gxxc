package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizSubSchool;
import com.cwb.platform.biz.service.SubSchoolService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

public class SubSchoolController extends BaseController<BizSubSchool,String> {
   @Autowired
   private SubSchoolService service;

    @Override
    protected BaseService<BizSubSchool, String> getBaseService() {
        return service;
    }
}
