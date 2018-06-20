package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.model.BizKsYk;
import com.cwb.platform.biz.service.KsYkService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 学员考试约考表
 * Created by Administrator on 2018/6/19.
 */
@RestController
@RequestMapping("/app/ksyk")
public class AppKsYkController extends QueryController<BizKsYk,String> {

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

    @RequestMapping("getUserExamInfo")
    public ApiResponse<Map<String,BizKsYk>> getUserExamInfo(String yhId){
        return service.getUserExamInfo(yhId);
    }

}
