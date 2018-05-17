package com.cwb.platform.sys.controller;

import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.SysRz;
import com.cwb.platform.sys.service.RzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志管理
 */
@RestController
@RequestMapping("/api/rz")
public class RzController extends BaseController<SysRz,String>{
    @Autowired
    private RzService service;

    @Override
    protected BaseService<SysRz, String> getBaseService() {
        return service;
    }
}
