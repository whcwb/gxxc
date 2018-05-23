package com.cwb.platform.biz.api;

import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.service.JobService;
import com.cwb.platform.util.bean.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * JOB定时任务处理接口
 */
@RestController
@RequestMapping("/job/")
public class JobApi {
    @Autowired
    private JobService jobService;


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

        //2、验证是否成功 ，如果失败就直接失败

        //3、

        List<BizOrder> list=jobService.orderFulfil();

        String messaget="";
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
                    messaget+="订单："+l.getDdId()+"处理异常\n";
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
