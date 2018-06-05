package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.service.JlService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 教练扩展表
 *
 */
@RestController
@RequestMapping("/api/jl")
public class JlController extends QueryController<BizJl,String> {
//public class JlController {
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

    @RequestMapping(value="/query", method={RequestMethod.GET})
    public ApiResponse<List<BizJl>> query(BizJl entity){
        return ApiResponse.success(service.query(entity));
    }
}
