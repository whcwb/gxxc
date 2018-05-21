package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizHd;
import com.cwb.platform.biz.model.BizZh;
import com.cwb.platform.biz.service.HdService;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/hd")
public class AppHdController extends AppUserBaseController {
    @Autowired
    private HdService service;

    /**
     * 分页查询。默认根据前台传递的值做精确搜索。需要其他搜索方式，请自行重新该方法
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizHd>> pager(BizZh entity, Page<BizHd> pager){
        return service.pager(pager);
    }

}
