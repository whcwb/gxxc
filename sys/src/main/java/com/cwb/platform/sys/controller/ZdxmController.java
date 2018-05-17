package com.cwb.platform.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.SysZdxm;
import com.cwb.platform.sys.service.ZdxmService;
import com.cwb.platform.util.bean.ApiResponse;

/**
 * 平台字典
 */
@RestController
@RequestMapping("/api/zdxm")
public class ZdxmController extends BaseController<SysZdxm, String> {
    @Autowired
    private ZdxmService zdxmService;

    @Override
    protected BaseService<SysZdxm, String> getBaseService() {
        return zdxmService;
    }

    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(SysZdxm entity) {
        this.zdxmService.add(entity);
        return ApiResponse.success();
    }

}
