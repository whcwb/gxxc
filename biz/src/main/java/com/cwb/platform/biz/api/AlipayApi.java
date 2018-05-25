package com.cwb.platform.biz.api;

import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.service.OrderService;
import com.cwb.platform.util.bean.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付宝接口
 */
@RestController
@RequestMapping("/alipay/")
public class AlipayApi {
    Logger payInfo = LoggerFactory.getLogger("access_info");

    @Autowired
    private OrderService oracleService;

    @Value("${debug_test}")
    private String debugTest;

    /**
     * 第三方支付回调
     * @param
     * @return
     */
    @RequestMapping(value="/orderFulfil", method={RequestMethod.POST})
    public ApiResponse<String> orderFulfil(@RequestParam(name = "ddId") String ddId ,@RequestParam(name = "ddZfpz") String ddZfpz){
//        pay_info


        payInfo.info("接收到支付宝的报文：");
        //1、验证报文是否正确

        //2、向支付宝验证本次交易的真实性

        BizOrder order=new BizOrder();
        // TODO: 2018/5/19 调试模式。
        if(debugTest!=null) {//调试
        order.setDdId(ddId);
        order.setDdZfpz(ddZfpz);//支付凭证ID(保存支付通道返回的CODE)
        }

        order.setPayMoney("2500");//支付宝，实际支付的金额
        order.setDdZftd("1");//设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
        oracleService.updateOrderPayTpye(order);
        return null;
    }
}
