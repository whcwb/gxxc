package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizJl;
<<<<<<< HEAD
import com.cwb.platform.biz.model.BizZh;
import com.cwb.platform.biz.service.JlService;
import com.cwb.platform.biz.service.ZhService;
=======
import com.cwb.platform.biz.service.JlService;
>>>>>>> ae32173d4149b92b4d44899eb17c98a59e47afdd
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
=======
/**
 * 教练扩展表
 *
 */
>>>>>>> ae32173d4149b92b4d44899eb17c98a59e47afdd
@RestController
@RequestMapping("/api/jl")
public class JlController extends BaseController<BizJl,String>{
    @Autowired
    private JlService service;

    @Override
    protected BaseService<BizJl, String> getBaseService() {
        return service;
    }
<<<<<<< HEAD
=======


>>>>>>> ae32173d4149b92b4d44899eb17c98a59e47afdd
}
