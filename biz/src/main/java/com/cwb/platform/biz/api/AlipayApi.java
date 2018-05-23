package com.cwb.platform.biz.api;

import com.cwb.platform.biz.service.JobService;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付宝接口
 */
@RestController
@RequestMapping("/alipay/")
public class AlipayApi {
    @Autowired
    private JobService jobService;
    /**
     * 订单处理成功
     * 1、查询所有完成的订单。
     * 2、给用户明细表，下发佣金。
     * 3、下发完佣金后，需要更新账户表
     * 4、给支付成功的用户生成邀请码，并生成二维码
     * @param entity
     * @return
     */
    @RequestMapping(value="/orderFulfil", method={RequestMethod.POST})
    public ApiResponse<String> orderFulfil(String entity){
        //1、报文验证 IP、时间戳、业务编号、md5校证值。

        //2、验证是否成功 ，如果失败就直接失败

        //3、




//        return jobService.orderFulfil();
        return null;
    }
}
