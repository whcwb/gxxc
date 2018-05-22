package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.app.service.AppUserService;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户实名表
 */
@RestController
@RequestMapping("/app/user")
public class AppUserController extends AppUserBaseController {
    @Autowired
    private AppUserService service;
    /**
     * 按全部、已付款、待付款来查询自己对应的一级，二级佣金订单
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizUser>> pager(BizOrder entity, Page<BizUser> pager){
        return service.pager(pager);
    }


}
