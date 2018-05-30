package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizCp;
<<<<<<< HEAD
import com.cwb.platform.biz.model.BizHd;
import com.cwb.platform.biz.service.CpService;
import com.cwb.platform.biz.service.HdService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

=======
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
>>>>>>> ae32173d4149b92b4d44899eb17c98a59e47afdd
@RestController
@RequestMapping("/api/cp")
public class CpController extends BaseController<BizCp,String>{
    @Autowired
    private CpService service;

    @Override
    protected BaseService<BizCp, String> getBaseService() {
        return service;
    }

<<<<<<< HEAD


=======
>>>>>>> ae32173d4149b92b4d44899eb17c98a59e47afdd
}
