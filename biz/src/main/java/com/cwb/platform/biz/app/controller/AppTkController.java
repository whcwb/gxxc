package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizTk;
import com.cwb.platform.biz.service.TkService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户退款操作
 *
 */
@RestController
@RequestMapping("/app/tk")
public class AppTkController extends AppUserBaseController{
    @Autowired
    private TkService service;

    /**
     * 用户退款操作
     */
    @PostMapping("/save")
    public ApiResponse<String> save(BizTk bizTk){
        BizPtyh ptyh= getAppCurrentUser();
        return service.saveAddTk(bizTk,ptyh);
    }

}
