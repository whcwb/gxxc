package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.service.OrderService;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController{
//public class OrderController extends BaseController<BizOrder,String>{
    @Autowired
    private OrderService service;

//    @Override
//    protected BaseService<BizOrder, java.lang.String> getBaseService() {
//        return service;
//    }


    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizOrder entity){
        return null;
    }

    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(BizOrder entity){
        return null;
    }

    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return null;
    }

    @RequestMapping(value="/removeIds", method={RequestMethod.POST})
    public ApiResponse<String> remove(String[] ids){
        return null;
    }


}
