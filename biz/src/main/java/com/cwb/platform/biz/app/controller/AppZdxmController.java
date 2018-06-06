package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.sys.model.SysZdxm;
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
@RequestMapping("/app/zdxm")
public class AppZdxmController extends AppUserBaseController {
    @Autowired
    private ZdxmService zdxmService;

    @RequestMapping(value="/getzdxm", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<SysZdxm>> getZdxmList(String typeCode){
        return ApiResponse.success(zdxmService.findByTypeCode(typeCode));
    }


}
