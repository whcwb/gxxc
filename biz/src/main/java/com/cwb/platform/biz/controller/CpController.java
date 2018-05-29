package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.biz.service.CpService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品管理
 *
 */
@RestController
@RequestMapping("/api/cp")
public class CpController extends BaseController<BizCp,String>{
    @Autowired
    private CpService service;

    @Override
    protected BaseService<BizCp, String> getBaseService() {
        return service;
    }

    /**
     * 新增
     * @param entity
     * @return
     */
//    @Override
//    @RequestMapping(value="/save", method={RequestMethod.POST})
//    public ApiResponse<String> save(BizCp entity){
//        return service.saveEntity(entity);
//    }

    /**
     * 修改
     * @param entity
     * @return
     */
//    @Override
//    @RequestMapping(value="/update", method={RequestMethod.POST})
//    public ApiResponse<String> update(BizCp entity){
//        return service.updateEntity(entity);
//    }

    /**
     * 活动推荐
     * @param entity
     * @return
     */
//    @RequestMapping(value="/hdtj", method={RequestMethod.POST})
//    public ApiResponse<String> activityRecommend(BizCp entity){
//        return service.activityRecommend(entity);
//    }



}
