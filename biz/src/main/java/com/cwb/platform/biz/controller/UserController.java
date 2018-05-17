package com.cwb.platform.biz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.UserService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController<BizUser,java.lang.String>{
    @Autowired
    private UserService service;

    @Override
    protected BaseService<BizUser, java.lang.String> getBaseService() {
        return service;
    }
}
