package com.cwb.platform.sys.controller;

import com.github.pagehelper.PageInfo;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.SysFw;
import com.cwb.platform.sys.model.SysGn;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.sys.model.SysZdlm;
import com.cwb.platform.sys.service.ZdlmService;
import com.cwb.platform.sys.service.ZdxmService;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 平台字典
 */
@RestController
@RequestMapping("/api/zd")
public class ZdLmController extends BaseController<SysZdlm, String> {
    @Autowired
    private ZdlmService zdlmService;

    @Override
    protected BaseService<SysZdlm, String> getBaseService() {
        return zdlmService;
    }

    @Override
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(SysZdlm entity) {
        this.zdlmService.add(entity);
        return ApiResponse.success();
    }


}
