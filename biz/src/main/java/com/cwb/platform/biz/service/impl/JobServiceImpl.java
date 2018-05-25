package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizOrderMapper;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.JobService;
import com.cwb.platform.biz.service.YjmxService;
import com.cwb.platform.biz.service.ZhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.ZXingCode;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class JobServiceImpl extends BaseServiceImpl<BizOrder,String> implements JobService {
    Logger log = LoggerFactory.getLogger("access_info");

    @Autowired
    private BizOrderMapper orderMapper;

    @Autowired
    private StringRedisTemplate redisDao;

    @Autowired
    private YjmxService yjmxService;

    @Autowired
    private BizPtyhMapper userMapper;

    @Value("${one_eevel_money_scale}")
    private String oneEevelMoneyScale;
    @Value("${two_eevel_money_scale}")
    private String twoEevelMoneyScale;
    @Value("${logo_file_url}")
    private String logoFileUrl;
    @Value("${qr_code_file_url}")
    private String qrCodeFileUrl;

    @Value("${order_money}")
    private String orderMoney;

    @Autowired
    private ZhService zhService;



    @Override
    protected Mapper<BizOrder> getBaseMapper() {
        return orderMapper;
    }
    @Override
    protected Class<?> getEntityCls(){
        return BizOrder.class;
    }
    /**
     * 订单处理成功
     * 1、查询所有完成的订单。
     * 2、给用户明细表，下发佣金。
     * 3、下发完佣金后，需要更新账户表
     * 4、给支付成功的用户生成邀请码，并生成二维码
     * @param
     * @return
     */
    public  List<BizOrder> orderFulfil(){
//        1、查询所有完成的订单，并且定时任务状态为0或者空的。
        SimpleCondition condition = new SimpleCondition(BizOrder.class);
        condition.eq(BizOrder.InnerColumn.ddZfzt.name(),"1");//支付状态（0,待支付 1、支付成功  2、支付失败）
        condition.eq(BizOrder.InnerColumn.jobType.name(),"0");//定时任务处理状态(0、待处理 1、处理成功 2、处理失败 )
        List<BizOrder> list=orderMapper.selectByExample(condition);//
        if(list==null|| list.isEmpty()){
            log.debug("未查到订单，处理结束");
        }
        RuntimeCheck.ifTrue(list==null|| list.isEmpty(),"未查到订单，处理结束");
        if (StringUtils.isBlank(oneEevelMoneyScale)){
            log.debug("一级比例不能为空");
        }
        RuntimeCheck.ifBlank(oneEevelMoneyScale,"一级比例不能为空");
        if (StringUtils.isBlank(twoEevelMoneyScale)){
            log.debug("二级比例不能为空");
        }
        RuntimeCheck.ifBlank(twoEevelMoneyScale,"二级比例不能为空");

        BigDecimal oneEevelMoney = new BigDecimal(oneEevelMoneyScale);
        BigDecimal twoEevelMoney = new BigDecimal(twoEevelMoneyScale);
        if (oneEevelMoney.add(twoEevelMoney).doubleValue()>=1){
            log.debug("系统配置一级比例："+oneEevelMoneyScale+" 一级比例："+twoEevelMoneyScale+" 之和大于1。系统禁止分派佣金！");
        }
        RuntimeCheck.ifTrue(oneEevelMoney.add(twoEevelMoney).doubleValue()>=1,"系统配置一级比例："+oneEevelMoneyScale+" 一级比例："+twoEevelMoneyScale+" 之和大于1。系统禁止分派佣金！");
        return list;

    }

    /**
     * 单个订单的处理
     * @return
     */
    public ApiResponse<String> updateOrderFulfilDispose(BizOrder l){
        List<String> updateUserList=new ArrayList<String>();
        Boolean retType=true;
        redisDao.boundValueOps("order_"+l.getDdId()).set(DateUtils.getNowTime(), 1, TimeUnit.DAYS);
        log.debug("1、订单ID："+l.getDdId()+"正在进行处理。。。");

        String yhSjid=l.getYhSjid();//上级ID
        String yhSsjid=l.getYhSsjid();//上上级ID
        //orderMoney
        BizOrder newBizOrder=new BizOrder();
        newBizOrder.setDdId(l.getDdId());
        if(!StringUtils.equals(l.getPayMoney(),orderMoney)){
            log.debug("9、订单编号："+l.getDdId()+"支付金额与系统配置金额不符合。系统跳过处理");
            newBizOrder.setJobType("2");
            newBizOrder.setJobDescribe("订单编号："+l.getDdId()+"支付金额与系统配置金额不符合。系统跳过处理");
//                messaget+=newBizOrder.getJobDescribe()+"\n";
            retType=false;
        }else{//正常流程
            String yhId=l.getYhId();
            if (StringUtils.isBlank(yhId)){
                log.debug("订单编号："+l.getDdId()+",用户ID不能为空");
                newBizOrder.setJobType("2");
                newBizOrder.setJobDescribe("订单编号："+l.getDdId()+",用户ID不能为空");
                retType=false;
            }else{
                //查询这个用户是否有已经处理成功的记录。 目的，防止一个用户支付两次，重复支付时，不能分佣金，需要联系用户做退款处理。
                SimpleCondition condition = new SimpleCondition(BizOrder.class);
                condition.eq(BizOrder.InnerColumn.yhId.name(),yhId);
                condition.eq(BizOrder.InnerColumn.ddZfzt.name(),"1");//支付状态（0,待支付 1、支付成功  2、支付失败）
                condition.eq(BizOrder.InnerColumn.jobType.name(),"1");//定时任务处理状态(0、待处理 1、处理成功 2、处理失败 )
                int i=orderMapper.selectCountByExample(condition);//
                if(i>0){
                    log.debug("订单编号："+l.getDdId()+",用户"+yhId+" 已有一笔缴费订单，此订单不能进行分派佣金");
                    newBizOrder.setJobType("2");
                    newBizOrder.setJobDescribe("订单编号："+l.getDdId()+",用户"+yhId+" 已有一笔缴费订单，此订单不能进行分派佣金");
                    retType=false;
                }else{
        //            计算出分的金额比例
                    BigDecimal orderMoney = new BigDecimal(l.getDdZfje());
                    BigDecimal oneEevelMoney = new BigDecimal(oneEevelMoneyScale);
                    BigDecimal twoEevelMoney = new BigDecimal(twoEevelMoneyScale);
                    BigDecimal oneEevelMoneyCount = orderMoney.multiply(oneEevelMoney);
                    BigDecimal twoEevelMoneyCount = orderMoney.multiply(twoEevelMoney);

                    //插入流水表1
                    BizYjmx newBizYjmx=new BizYjmx();
                    newBizYjmx.setId(genId());
                    newBizYjmx.setZjId(l.getDdId());
                    newBizYjmx.setYhId(yhSjid);//上级ID
                    newBizYjmx.setZjJe(oneEevelMoneyCount.doubleValue());
                    newBizYjmx.setZjFs("1");//费用方式 ZDCLK0053 (1 佣金 -1 提现)
                    newBizYjmx.setCjsj(DateUtils.getNowTime());
                    newBizYjmx.setZjZt("1");//提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 ) 提现操作默认0 佣金操作默认1
                    yjmxService.save(newBizYjmx);
                    log.debug("2-1、订单ID："+l.getDdId()+"。插入流水表："+newBizYjmx.toString());
        //          插入流水表2
                    newBizYjmx=new BizYjmx();
                    newBizYjmx.setId(genId());
                    newBizYjmx.setZjId(l.getDdId());
                    newBizYjmx.setYhId(yhSsjid);//上上级ID
                    newBizYjmx.setZjJe(twoEevelMoneyCount.doubleValue());
                    newBizYjmx.setZjFs("1");//费用方式 ZDCLK0053 (1 佣金 -1 提现)
                    newBizYjmx.setCjsj(DateUtils.getNowTime());
                    newBizYjmx.setZjZt("1");//提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 ) 提现操作默认0 佣金操作默认1
                    yjmxService.save(newBizYjmx);
                    log.debug("2-2、订单ID："+l.getDdId()+"。插入流水表："+newBizYjmx.toString());
                    if(StringUtils.isNotEmpty(yhSjid)){
                        updateUserList.add(yhSjid);
                    }
                    if(StringUtils.isNotEmpty(yhSsjid)){
                        updateUserList.add(yhSsjid);
                    }
                    newBizOrder.setJobType("1");

                    String  yhZsyqm=genId();
                    File logoFile = new File(logoFileUrl);
                    String yhZsyqmImg=yhZsyqm+".png";
                    String note="您的好友："+l.getYhXm()+" 邀请您";
                    ZXingCode.drawLogoQRCode(logoFile, new File(qrCodeFileUrl+"\\"+yhZsyqmImg), yhZsyqm, note);
                    log.debug("3、用户："+l.getYhXm()+"。生成邀请码成功");

                    BizPtyh user=new BizPtyh();
                    user.setId(l.getYhId());
                    user.setYhZsyqm(yhZsyqm);//用户自己邀请码
                    user.setYhZsyqmImg(yhZsyqmImg);//用户自己邀请码

                    userMapper.updateByPrimaryKeySelective(user);
                    if(updateUserList.size()>0){
                        zhService.userAccountUpdate(updateUserList);
                        log.debug("4、更新账户表");
                    }else{
                        // TODO: 2018/5/25 这里需要预警。一个用户没有上级账户的话，就需要人工进行判定是否异常
                        log.debug("4、更新账户表,该用户没有上级账户，需要核实*******");
                    }
                }
            }
        }
        orderMapper.updateByPrimaryKeySelective(newBizOrder);
            log.debug("5、更新订单主表。完成订单的分派");
        return retType? ApiResponse.success():ApiResponse.fail(newBizOrder.getJobDescribe());
    }
}
