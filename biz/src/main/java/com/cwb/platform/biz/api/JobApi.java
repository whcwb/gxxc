package com.cwb.platform.biz.api;

import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.service.JobService;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.MD5Util;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * JOB定时任务处理接口
 */
@RestController
@RequestMapping("/job/")
public class JobApi {
    @Autowired
    private JobService jobService;
    @Value("${JOB_KEY}")
    private String jobKey;


    @Autowired
    private StringRedisTemplate redisDao;

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

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String host = request.getRemoteHost();
        if(!StringUtils.equals(host,"127.0.0.1")){
            return ApiResponse.fail("ip异常");
        }
        //MD5Util.MD5Encode(jobKey + DateUtils.getDateStr(new Date(), "yyyy-MM-dd HH:mm"),null);
        DateTime dateTime = DateTime.now();
        DateTime minusMinutes = dateTime.minusMinutes(1);
        String encode = MD5Util.MD5Encode(jobKey + DateUtils.getDateStr(dateTime.toDate(), "yyyy-MM-dd HH:mm"), null);
        String encode1 = MD5Util.MD5Encode(jobKey + DateUtils.getDateStr(minusMinutes.toDate(), "yyyy-MM-dd HH:mm"), null);
        if(!StringUtils.equals(entity,encode) && !StringUtils.equals(entity,encode1)){
            return ApiResponse.fail("不是当前的任务");
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
}
