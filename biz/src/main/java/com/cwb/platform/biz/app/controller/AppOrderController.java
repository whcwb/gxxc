package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.app.service.AppOrderService;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *  订单业务查询
 *
 */
@RestController
@RequestMapping("/app/order")
public class AppOrderController extends AppUserBaseController {
    @Autowired
    private AppOrderService service;

    /**
     * 按全部、已付款、待付款来查询自己对应的一级，二级佣金订单
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizOrder>> pager(BizOrder entity, Page<BizOrder> pager){
//        RuntimeCheck.ifNull(user,"用户不存在");
        return service.pager(pager);
    }

    /**
     * 订单新增
     * @param entity
     * ddZftd //支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
     * cpid//选择要支付的产品ID
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<Map<String,String>> save(BizOrder entity,HttpServletRequest request){
        return service.saveAddOrder(entity,request);
    }


}
