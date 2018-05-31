package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizCjd;
import com.cwb.platform.biz.service.CjdService;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品管理
 *
 */
@RestController
@RequestMapping("/app/cjd")
public class AppCjdController extends AppUserBaseController{
    @Autowired
    private CjdService service;

    /**
     * 用户上传成绩单，对教练进行评分
     */
    @PostMapping("/save")
    public ApiResponse<String> giveMark(BizCjd bizCjd){
        return service.giveMark(bizCjd);
    }



}
