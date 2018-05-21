package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizZh;
import com.cwb.platform.biz.service.ZhService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账户表
 */
@RestController
@RequestMapping("/app/zh")
public class AppZhController extends AppUserBaseController {
    @Autowired
    private ZhService service;
    @RequestMapping(value="/get", method={RequestMethod.GET})
    public ApiResponse<BizZh> get(){
        BizPtyh user = getAppCurrentUser();
        return ApiResponse.success(service.findById(user.getId()));
    }

}
