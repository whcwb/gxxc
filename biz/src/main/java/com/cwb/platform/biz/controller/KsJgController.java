package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizKsJg;
import com.cwb.platform.biz.service.KsjgService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 考试结果记录表
 * Created by Administrator on 2018/6/19.
 */
@RestController
@RequestMapping("/api/ksjg")
public class KsJgController extends QueryController<BizKsJg,String> {

    @Autowired
    private KsjgService service;

    @Override
    protected BaseService<BizKsJg, String> getBaseService() {
        return service;
    }

    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizKsJg entity){
        return service.validAndSave(entity);
    }
}
