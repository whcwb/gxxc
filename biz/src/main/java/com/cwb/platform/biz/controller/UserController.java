package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.UserService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController extends QueryController<BizUser,String> {
//public class UserController {
    @Autowired
    private UserService service;

    @Override
    protected BaseService<BizUser, String> getBaseService() {
        return service;
    }
}
