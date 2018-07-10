package com.cwb.platform.biz.api;

import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.service.JobService;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.MD5Util;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * JOB定时任务处理接口
 */
@RestController
@RequestMapping("/job/")
public class JobApi {
    @Autowired
    private JobService jobService;
    @Value("${JOB.KEY}")
    private String jobKey;
    @Value("${JOB.TOKEN}")
    private String jobToken;
    @Value("${JOB.HOST}")
    private String jobHost;

    @Resource(name = "wxPayService")
    private WxPayService wxService;

    @Autowired
    private StringRedisTemplate redisDao;

    /**
     * 订单处理成功
     * 1、查询所有完成的订单。
     * 2、给用户明细表，下发佣金。
     * 3、下发完佣金后，需要更新账户表
     * 4、给支付成功的用户生成邀请码，并生成二维码
     * @param key
     * @return
     */
    @RequestMapping(value="/orderFulfil", method={RequestMethod.POST})
    public ApiResponse<String> orderFulfil(@RequestParam(value = "key",required = false) String key,@RequestParam(value = "token",required = false) String token){
        //1、报文验证 IP、时间戳、业务编号、md5校证值。

        if(StringUtils.isBlank(token)){
            if(StringUtils.isBlank(key)){
                return ApiResponse.fail("密钥为空");
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String host = request.getRemoteHost();
            if(StringUtils.indexOf(jobHost,host) < 0 ){
                return ApiResponse.fail("ip异常");
            }
            //MD5Util.MD5Encode(jobKey + DateUtils.getDateStr(new Date(), "yyyy-MM-dd HH:mm"),null);
            DateTime dateTime = DateTime.now();
            DateTime minusMinutes = dateTime.minusMinutes(1);
            String encode = MD5Util.MD5Encode(jobKey + DateUtils.getDateStr(dateTime.toDate(), "yyyy-MM-dd HH:mm"), null);
            String encode1 = MD5Util.MD5Encode(jobKey + DateUtils.getDateStr(minusMinutes.toDate(), "yyyy-MM-dd HH:mm"), null);
            if(!StringUtils.equals(key,encode) && !StringUtils.equals(key,encode1)){
                return ApiResponse.fail("不是当前的任务");
            }
        }else {
            if(!StringUtils.equals(jobToken,token)){
                return ApiResponse.fail("token错误");
            }
        }


        //2、验证是否成功 ，如果失败就直接失败

        //3、

        List<BizOrder> list=jobService.orderFulfil();

        String messaget="";
        if(CollectionUtils.isEmpty(list)){
            messaget = "未查询到订单";
        }
        for(BizOrder l:list){
            String value = redisDao.boundValueOps("order_"+l.getDdId()).get();
            if(StringUtils.isEmpty(value)){
                try {
                    ApiResponse<String> ret=jobService.updateOrderFulfilDispose(l);
                    if(!ret.isSuccess()){
                        messaget+=ret.getMessage()+"\n";
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    messaget+="订单："+l.getDdId()+"处理异常\n"; // TODO: 2018/6/8 数据库异常，需要回写到定时任务中
                }
                finally {
                    redisDao.delete("order_"+l.getDdId());
                }

            }else{
                messaget+="订单编号："+l.getDdId()+"已被其它应用于"+value+"处理。系统跳过处理\n";
            }
        }

        return ApiResponse.success(messaget);

//        return null;
    }

    /**
     * 定时任务 定时对账
     * <pre>
     * 下载对账单
     * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
     * 注意：
     * 1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
     * 2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
     * 3、对账单中涉及金额的字段单位为“元”。
     * 4、对账单接口只能下载三个月以内的账单。
     * 接口链接：https://api.mch.weixin.qq.com/pay/downloadbill
     * 详情请见: <a href="https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_6">下载对账单</a>
     * </pre>
     *
     * @param billDate   对账单日期 bill_date	下载对账单的日期，格式：20140603
     * @param billType   账单类型	bill_type	ALL，返回当日所有订单信息，默认值，SUCCESS，返回当日成功支付的订单，REFUND，返回当日退款订单
     * @param deviceInfo 设备号	device_info	非必传参数，终端设备号
     * @return 保存到本地的临时文件
     */
    @PostMapping("/downloadBill")
    public ApiResponse<List<String>> downloadBill(@RequestParam String billDate,
                                        @RequestParam String billType,
                                        @RequestParam(name = "deviceInfo",required=false) String deviceInfo) throws WxPayException {
        List<String> retMessage=new ArrayList<String>();
         WxPayBillResult billResult=wxService.downloadBill(billDate, billType, "", deviceInfo);
         if(billResult!=null&&billResult.getWxPayBillBaseResultLst().size()>0){
             retMessage= jobService.billContrast(billResult,billDate);
         }

        ApiResponse<List<String>> res = new ApiResponse<List<String>>();
        res.setMessage("操作成功");
        res.setResult(retMessage);
        return res;
    }
}
