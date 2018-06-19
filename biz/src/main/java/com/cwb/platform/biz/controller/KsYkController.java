package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizKsYk;
import com.cwb.platform.biz.service.KsYkService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学员考试约考表
 * Created by Administrator on 2018/6/19.
 */
@RestController
@RequestMapping("/api/ksyk")
public class KsYkController extends QueryController<BizKsYk,String> {

    @Autowired
    private KsYkService service;

    @Override
    protected BaseService<BizKsYk, String> getBaseService() {
        return service;
    }

    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizKsYk entity){
        return service.validAndSave(entity);
    }
}
