package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.app.service.impl.AppYjmxServiceImpl;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/yjmx")
public class AppYjmxController extends AppUserBaseController {
//public class YjmxController{
    @Autowired
    private AppYjmxServiceImpl service;

    /**
     * 账单明细
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizYjmx>> pager(BizYjmx entity, Page<BizYjmx> pager){
        return service.pager(pager);
    }

}
