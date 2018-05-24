package com.cwb.platform.biz.api;

import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.service.OrderService;
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
    private OrderService oracleService;
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
        //1、验证报文是否正确

        //2、向支付宝验证本次交易的真实性

        BizOrder order=new BizOrder();
        order.setPayMoney("2500");//支付宝，实际支付的金额
        order.setDdZftd("1");//设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
        order.setDdZfpz("支付宝凭证ID-测试");//支付凭证ID(保存支付通道返回的CODE)
        oracleService.updateOrderPayTpye(order);
//        return jobService.orderFulfil();
        return null;
    }
}
