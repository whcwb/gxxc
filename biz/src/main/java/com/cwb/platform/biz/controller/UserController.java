package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
//public class UserController extends BaseController<BizUser,java.lang.String>{
public class UserController {
    @Autowired
    private UserService service;

//    @Override
//    protected BaseService<BizUser, java.lang.String> getBaseService() {
//        return service;
//    }
}
