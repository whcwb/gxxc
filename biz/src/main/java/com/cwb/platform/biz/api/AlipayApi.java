package com.cwb.platform.biz.api;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.model.BizZfrz;
import com.cwb.platform.biz.service.OrderService;
import com.cwb.platform.biz.service.ZfrzService;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.MathUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝接口
 */
@RestController
@RequestMapping("/alipay/")
public class AlipayApi {
    Logger logger = LoggerFactory.getLogger("access_info");

//  应用id(app_id)
    @Value("${alipay.app_id}")
    private String appId;
    //APPA应用私钥(private_key)
    @Value("${alipay.app_privaie_key_pkcs8}")
    private String appPrivaieKey;
    //编码格式(charset)
    @Value("${alipay.charset}")
    private String charset;
//    支付宝公钥
    @Value("${alipay.alipay_public_key}")
    private String alipayPublicKey;
//    网关(gateway)请求支付的接口
    @Value("${alipay.gateway}")
    private String gateway;
//  签名类型  默认为：RSA2
    @Value("${alipay.sign_type}")
    private String signType;


    /**
     * 支付日志
     */
    @Autowired
    private ZfrzService zfrzService;

    @Autowired
    private OrderService oracleService;

    /**
     * 第三方支付回调
     * @param
     * @return
     */
    @RequestMapping(value="/orderFulfil", method={RequestMethod.POST})
    public void orderFulfil(HttpServletRequest request,HttpServletResponse response){

        try{
        String orderCode = null;//回调 支付订单号
        String totalFee = null;//支付金额
        boolean retType=false;
        ApiResponse<String> res=new ApiResponse<String>();

        BizZfrz payLog=new BizZfrz();
        payLog.setZfLx("1");
//        payLog.setZfBw(result);// 支付原始报文

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean flag=false;
        try {
            payLog.setZfBw(JSONObject.fromObject(requestParams).toString());
            flag = AlipaySignature.rsaCheckV1(params, alipayPublicKey, charset,signType);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            payLog.setZfMs("报文转MAP出错");
        }
        if(flag){//验证成功
            payLog.setZfPz(params.get("trade_no"));//支付凭证id
            orderCode=params.get("out_trade_no");
            String receipt_amount=params.get("out_trade_no");//商家在交易中实际收到的款项，单位为元
            totalFee=(MathUtil.stringToDouble(receipt_amount)*100)+"";
            //支付宝支付成功
            if(StringUtils.equals(params.get("trade_status"),"TRADE_SUCCESS")||StringUtils.equals(params.get("trade_status"),"TRADE_FINISHED")){
                BizOrder order=new BizOrder();
                order.setDdId(orderCode);//商户订单
                order.setPayMoney(totalFee);//支付宝，实际支付的金额
                order.setDdZftd("1");//设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
                res= oracleService.updateOrderPayTpye(order);
                if(res.isSuccess()){
                    payLog.setZfClzt("1");// 支付处理状态
                    payLog.setZfJe(totalFee);// 支付处理状态
                    payLog.setZfMs(res.getMessage());//日志描述
                    logger.info("out_trade_no: " + orderCode + " pay SUCCESS!");
                    retType=true;
                }else{
                    payLog.setZfMs("业务处理失败"+"  订单ID: "  + orderCode + res.getMessage());
                    this.logger.error("out_trade_no: "  + orderCode + " result_code is FAIL3");
                }
            }
        }else{
            payLog.setZfMs("验签失败"+"  订单ID: "  + orderCode + " result_code is FAIL");
            this.logger.error("out_trade_no: " + orderCode + " check signature FAIL");
        }

        if(retType){
            System.out.println("支付成功：订单号："+orderCode+",支付金额："+totalFee);

            logger.info("支付成功：订单号："+orderCode+",支付金额："+totalFee);
            response.getWriter().write("error");//操作成功返回。
        }else{
            response.getWriter().write("error");//操作失败返回。
            System.out.println("支付宝支付 回调 ：*-************支付失败");
            logger.info("支付宝支付 回调 ：*-************支付失败"+res.getMessage());
        }

        zfrzService.addObject(payLog);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
