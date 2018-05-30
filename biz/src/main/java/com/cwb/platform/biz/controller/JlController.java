package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.service.JlService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 教练扩展表
 *
 */
@RestController
@RequestMapping("/api/jl")
public class JlController extends BaseController<BizJl,String>{
    @Autowired
    private JlService service;

    @Override
    protected BaseService<BizJl, String> getBaseService() {
        return service;
    }


    /**
     * 更新教练认证状态
     *
     * @param obd
     * @return
     */
    @PostMapping("/updateyhrz")
    public ApiResponse<String> updateYhRz(BizPtyh obd) {
        return service.updateYhRz(obd);
    }
}
