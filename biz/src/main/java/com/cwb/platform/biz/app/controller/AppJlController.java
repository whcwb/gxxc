package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.app.service.AppJlService;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 教练列表
 *
 */
@RestController
@RequestMapping("/app/jl")
public class AppJlController extends AppUserBaseController {
//public class JlController {
    @Autowired
    private AppJlService service;

    /**
     * 教练列表
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizJl>> pager(BizJl entity, Page<BizJl> pager){
        return service.pager(pager);
    }
}
