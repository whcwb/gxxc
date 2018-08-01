package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.*;
import com.cwb.platform.biz.model.*;
import com.cwb.platform.biz.service.*;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.MathUtil;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.binarywang.wxpay.bean.result.WxPayBillBaseResult;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//import java.math.BigDecimal;

@Service
public class JobServiceImpl extends BaseServiceImpl<BizOrder, String> implements JobService {
    Logger log = LoggerFactory.getLogger("access_info");

    @Autowired
    private BizOrderMapper orderMapper;
    @Autowired
    private OrderService oracleService;

    @Autowired
    private BizBillContrastMapper billContrastMapper;


    @Autowired
    private BizTjDzMapper bizTjDzMapper;

    @Autowired
    private StringRedisTemplate redisDao;


    @Autowired
    private YjmxService yjmxService;

    @Autowired
    private BizPtyhMapper userMapper;

    @Autowired
    private CpService cpService;

    @Autowired
    private BizTjMapper tjMapper;


    @Resource(name = "wxPayService")
    private WxPayService wxService;


    @Value("${logo_file_url}")
    private String logoFileUrl;
    @Value("${qr_code_file_url}")
    private String qrCodeFileUrl;

    @Autowired
    private ZhService zhService;


    @Override
    protected Mapper<BizOrder> getBaseMapper() {
        return orderMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return BizOrder.class;
    }

    /**
     * 订单处理成功
     * 1、查询所有完成的订单。
     * 2、给用户明细表，下发佣金。
     * 3、下发完佣金后，需要更新账户表
     * 4、给支付成功的用户生成邀请码，并生成二维码
     *
     * @param
     * @return
     */
    public List<BizOrder> orderFulfil() {
//        1、查询所有完成的订单，并且定时任务状态为0或者空的。
        SimpleCondition condition = new SimpleCondition(BizOrder.class);
        condition.eq(BizOrder.InnerColumn.ddZfzt.name(), "1");//支付状态（0,待支付 1、支付成功  2、支付失败）
        condition.eq(BizOrder.InnerColumn.jobType.name(), "0");//定时任务处理状态(0、待处理 1、处理成功 2、处理失败 )
        List<BizOrder> list = orderMapper.selectByExample(condition);//
        if (list == null || list.isEmpty()) {
            log.debug("未查到订单，处理结束");
        }
        return list;
    }


//    /**
//     * 订单处理定时任务
//     */
//    @Override
//    public void orderFulfilJob(){
//        final List<BizOrder> list = orderFulfil();
//        for(BizOrder l:list){
//            String value = redisDao.boundValueOps("order_"+l.getDdId()).get();
//            if(StringUtils.isEmpty(value)){
//                try {
//                    ApiResponse<String> ret=updateOrderFulfilDispose(l);
//                    if(!ret.isSuccess()){
//                        log.debug(ret.getMessage());
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                    log.debug("订单："+l.getDdId()+"处理异常"); // TODO: 2018/6/8 数据库异常，需要回写到定时任务中
//                }
//                finally {
//                    redisDao.delete("order_"+l.getDdId());
//                }
//
//            }else{
//                log.debug("订单编号："+l.getDdId()+"已被其它应用于"+value+"处理。系统跳过处理");
//            }
//        }
//    }





    /**
     * 单个订单的处理
     *
     *
     * @return
     */
    public ApiResponse<String> updateOrderFulfilDispose(BizOrder l) {
        List<String> updateUserList = new ArrayList<>();
        Boolean retType = true;
        redisDao.boundValueOps("order_" + l.getDdId()).set(DateUtils.getNowTime(), 1, TimeUnit.DAYS);
        log.debug("1、订单ID：" + l.getDdId() + "正在进行处理。。。");

        BizOrder newBizOrder = new BizOrder();
        newBizOrder.setDdId(l.getDdId());

        BizCp bizCp = cpService.findById(l.getCpId());

        if(ObjectUtils.isEmpty(bizCp)){
            log.debug("产品 id " + l.getCpId() + "找不到相对应的产品信息" );
            retType = false;

            newBizOrder.setJobType("2");
            newBizOrder.setJobDescribe("产品 id " + l.getCpId() + "找不到相对应的产品信息");
            orderMapper.updateByPrimaryKeySelective(newBizOrder);
            log.debug("5、更新订单主表。完成订单的分派");
            return retType ? ApiResponse.success() : ApiResponse.fail(newBizOrder.getJobDescribe());
        }
        String yhSjid = l.getYhSjid();//上级ID
        String yhSsjid = l.getYhSsjid();//上上级ID
        //验证订单金额 和 支付金额做比较，如果订单金额  和支付金额不一至，就不对该订单进行分佣
        if ((MathUtil.stringToDouble(l.getPayMoney()) - MathUtil.stringToDouble(bizCp.getCpJl()))!=0) {
            log.debug("9、订单编号：" + l.getDdId() + "支付金额与产品配置金额不符合。系统跳过处理");
            newBizOrder.setJobType("2");
            newBizOrder.setJobDescribe("订单编号：" + l.getDdId() + "支付金额与系统配置金额不符合。系统跳过处理 。产品ID:"+bizCp.getId()+" 产品金额："+bizCp.getCpJl()+" 支付金额："+l.getPayMoney());
            retType = false;
        } else if(StringUtils.equals(bizCp.getCpYj(),"1")){    //正常流程
            String yhId = l.getYhId();
            if (StringUtils.isBlank(yhId)) {
                log.debug("订单编号：" + l.getDdId() + ",用户ID不能为空");
                newBizOrder.setJobType("2");
                newBizOrder.setJobDescribe("订单编号：" + l.getDdId() + ",用户ID不能为空");
                retType = false;
            } else {
//                用户需要支持多次支付
//                //查询这个用户是否有已经处理成功的记录。 目的，防止一个用户支付两次，重复支付时，不能分佣金，需要联系用户做退款处理。
//                SimpleCondition condition = new SimpleCondition(BizOrder.class);
//                condition.eq(BizOrder.InnerColumn.yhId.name(), yhId);
//                condition.eq(BizOrder.InnerColumn.ddZfzt.name(), "1");//支付状态（0,待支付 1、支付成功  2、支付失败）
//                condition.eq(BizOrder.InnerColumn.jobType.name(), "1");//定时任务处理状态(0、待处理 1、处理成功 2、处理失败 )
//                int i = orderMapper.selectCountByExample(condition);//
                int i=0;
                if (i > 0) {
                    log.debug("订单编号：" + l.getDdId() + ",用户" + yhId + " 已有一笔缴费订单，此订单不能进行分派佣金");
                    newBizOrder.setJobType("2");
                    newBizOrder.setJobDescribe("订单编号：" + l.getDdId() + ",用户" + yhId + " 已有一笔缴费订单，此订单不能进行分派佣金");
                    retType = false;
                } else {
                    // 根据产品表判断是否 要分佣
                    if(StringUtils.equals(bizCp.getCpYj(),"1")){// 要分佣

                        //查询该订单已分佣金额
                        String types=userMapper.fyMoney(l.getDdId());
                        boolean type=true;
                        if(StringUtils.isNotEmpty(StringUtils.trim(types))){
                            if(Double.parseDouble(types)>0){
                                type=false;
                            }
                        }
                        if(!type){
                            log.debug("订单编号：" + l.getDdId() + ",已有分佣数据，不能再次进行分佣");
                            newBizOrder.setJobType("2");
                            newBizOrder.setJobDescribe("订单编号：" + l.getDdId() + ",已有分佣数据，不能再次进行分佣");
                            retType = false;

                        }else{
                            //插入流水表1
                            BizYjmx newBizYjmx = new BizYjmx();
                            newBizYjmx.setId(genId());
                            newBizYjmx.setZjId(l.getDdId());
                            newBizYjmx.setYhId(yhSjid);//上级ID
                            newBizYjmx.setZjJe(bizCp.getCpYjyj());
                            newBizYjmx.setZjFs("1");//费用方式 ZDCLK0053 (1 佣金 -1 提现)
                            newBizYjmx.setCjsj(DateUtils.getNowTime());
                            newBizYjmx.setZjZt("1");//提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 ) 提现操作默认0 佣金操作默认1
                            newBizYjmx.setMxlx("2");//明细类型  ZDCLK0066 1、付款 2、分佣 3、消费 4、提现
                            if (StringUtils.isNotEmpty(yhSjid)) {
                                yjmxService.save(newBizYjmx);
                                updateUserList.add(yhSjid);
                            }
                            log.debug("2-1、订单ID：" + l.getDdId() + "。插入流水表：" + newBizYjmx.toString());
                            //插入流水表2
                            newBizYjmx = new BizYjmx();
                            newBizYjmx.setId(genId());
                            newBizYjmx.setZjId(l.getDdId());
                            newBizYjmx.setYhId(yhSsjid);//上上级ID
                            newBizYjmx.setZjJe(bizCp.getCpRjyj());
                            newBizYjmx.setZjFs("1");//费用方式 ZDCLK0053 (1 佣金 -1 提现)
                            newBizYjmx.setCjsj(DateUtils.getNowTime());
                            newBizYjmx.setZjZt("1");//提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 ) 提现操作默认0 佣金操作默认1
                            newBizYjmx.setMxlx("2");//明细类型  ZDCLK0066 1、付款 2、分佣 3、消费 4、提现
                            if (StringUtils.isNotEmpty(yhSsjid)) {
                                yjmxService.save(newBizYjmx);
                                updateUserList.add(yhSsjid);
                            }
                            log.debug("2-2、订单ID：" + l.getDdId() + "。插入流水表：" + newBizYjmx.toString());

                            if (updateUserList.size() > 0) {
                                zhService.userAccountUpdate(updateUserList);
                                log.debug("4、更新账户表");
                            } else {
                                //2018/5/25 这里需要预警。一个用户没有上级账户的话，就需要人工进行判定是否异常
                                log.debug("4、更新账户表,该用户没有上级账户，需要核实*******");
                            }
                        }

                    }

                    if(retType){
                        newBizOrder.setJobType("1");// 定时任务操作成功
                    }
                }
            }
        }else{
            newBizOrder.setJobType("1");// 定时任务操作成功
            newBizOrder.setJobDescribe("订单编号：" + l.getDdId() +" 产品："+bizCp.getCpMc()+"，不参与分佣");
        }

        orderMapper.updateByPrimaryKeySelective(newBizOrder);
        log.debug("5、更新订单主表。完成订单的分派");
        return retType ? ApiResponse.success() : ApiResponse.fail(newBizOrder.getJobDescribe());
    }

//    /**
//     * 系统对账处理  该接口已经不用了
//     * @param billResult
//     * @return
//     */
//    public List<String> billContrast(WxPayBillResult billResult,String billDate){
//        List<String> ret=new ArrayList<String>();
//        log.debug("1、开始处理时间为："+billDate+"的对账数据。共"+billResult.getTotalRecord()+"笔。总交易额:"+billResult.getTotalFee() );
//        List<WxPayBillBaseResult> billResultList=billResult.getWxPayBillBaseResultLst();
//        List<BizBillContrast> addBizBillList=new ArrayList<BizBillContrast>();
//        String orderList="";//待缴费状态的订单
//        if(billResultList!=null && billResultList.size()>0){
//            int i=1;
//            for(WxPayBillBaseResult l:billResultList){
//                BizBillContrast bizBill= new BizBillContrast();
//                bizBill.setId(genId());
//                bizBill.setTradeTime(billDate);
//                bizBill.setOpenId(l.getOpenId());
//                bizBill.setTradeType(l.getTradeType());//交易类型   JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里  MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
//                bizBill.setTradeState(l.getTradeState());//交易状态
//                bizBill.setTotalFee(l.getTotalFee());//总金额(元)
//                bizBill.setRefundId(l.getRefundId());//微信退款单号
//                bizBill.setRefundChannel(l.getRefundChannel());//退款类型
//                bizBill.setPatType("2");//支付通道(1、支付宝 2、微信)
//                bizBill.setOriginalMessage(l.toString());//对账文件的原始报文
//                bizBill.setRefundFee(l.getSettlementRefundFee());//退款金额
//                bizBill.setOrderId(l.getOutTradeNo());
//
//                Boolean bizType=true;
//                String errorMessage="操作成功";
//                log.debug("2-"+i+"、开始处理第："+i+"条数据");
//                log.debug("2-"+i+"、对账的文件："+l.toString());
//                String transactionId=l.getTransactionId();//支付凭证
//                String orderId=l.getOutTradeNo();//系统订单号
//
//                BizOrder newBizOrder=new BizOrder();
//                newBizOrder.setDdId(orderId);
//
//                SimpleCondition condition = new SimpleCondition(BizOrder.class);
//                condition.eq(BizOrder.InnerColumn.ddZfpz.name(), transactionId);//支付状态（0,待支付 1、支付成功  2、支付失败）
//                condition.eq(BizOrder.InnerColumn.ddId.name(), orderId);//定时任务处理状态(0、待处理 1、处理成功 2、处理失败 )
//                List<BizOrder> list = orderMapper.selectByExample(condition);//
//                if (list == null || list.isEmpty()) {
//                    bizType=false;
//                    errorMessage="微信支付流水ID："+transactionId+"未找到这笔订单";
//                    log.debug("2-"+i+"、对账的文件："+l.toString());
//                }
//                if(bizType) {
//                    BizOrder order = list.get(0);
//                    String billContrastType = order.getBillContrastType();//对账状态：0未对账      1、已对账      2、对账异常
////                    对账成功的，就跳过处理
//                    if (StringUtils.equals(billContrastType, "1")) {
//                        bizType = false;
//                        errorMessage = "微信支付流水ID：" + transactionId + " 该订单已经对账成功 ，不需要再次对账处理";
//                        log.debug("2-" + i + "、" + errorMessage);
//                        newBizOrder.setBillContrastType("2");
//                        newBizOrder.setBillContrastMsg(errorMessage);
//                        orderMapper.updateByPrimaryKeySelective(newBizOrder);
//                    } else {
//                        //交易类型   JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里  MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
//                        String tradeType = l.getTradeType();
//                        //交易状态
//                        //                SUCCESS: 对应收入 REFUND: 对应支出-退款 REVOKED: 对应支出-撤销
//                        //                这里有几点注意：
//                        //                1、账单的交易状态，和订单的交易状态没有关系，发生过退款的订单，在这里依然有一条success
//                        //                交易状态不会变更 2、只有发生过支付（进账）的订单，在撤销后才会发生支出并出现在对账单里。
//                        String tradeState = l.getTradeState();
//                        String totalFee = l.getTotalFee();//交易金额
//                        String settlementRefundFee = l.getSettlementRefundFee();//退款金额
//                        if (StringUtils.equals(tradeState, "SUCCESS")) {//处理成功
//                            if (MathUtil.stringToDouble(totalFee) - order.getDdZfje() / 100 != 0) {
//                                bizType = false;
//                                errorMessage = "微信支付流水ID：" + transactionId + " 金额与订单中金额(" + (order.getDdZfje() / 100) + ")不匹配";
//                                log.debug("2-" + i + "、对账的文件：" + l.toString());
//                                newBizOrder.setBillContrastType("2");
//                                newBizOrder.setBillContrastMsg(errorMessage);
//                                orderMapper.updateByPrimaryKeySelective(newBizOrder);
//                            } else {
//                                String ddZt = order.getDdZt();//获取订单状态(ZDCLK0037 1、待缴费 2、已缴费 3、已退费)
//                                if (StringUtils.equals(ddZt, "1") && StringUtils.equals(bizBill.getRefundFee(), "0.00")) {
//                                    orderList += order.getDdId() + ",";
//                                }
//                                //修改订单表
//                                newBizOrder.setBillContrastType("1");
//                                newBizOrder.setBillContrastMsg("");
//                                orderMapper.updateByPrimaryKeySelective(newBizOrder);
//                            }
//                        } else if (StringUtils.equals(tradeState, "REVOKED") || StringUtils.equals(tradeState, "REFUND")) {//撤销
//                            //后台退费后，这里会有撤销操作。
//                            if (StringUtils.isNotEmpty(orderList)) {
//                                orderList = orderList.replaceAll(order.getDdId() + ",", "");
//                            }
//                            errorMessage = "微信支付流水ID：" + transactionId + " 于" + l.getTradeTime() + "产生了退款操作。请紧急联系管理人员进行核实";
//                            log.debug("2-" + i + "、" + errorMessage);
//                            newBizOrder.setBillContrastType("2");
//                            newBizOrder.setBillContrastMsg(errorMessage);
//                            orderMapper.updateByPrimaryKeySelective(newBizOrder);
//                        }
//
//                    }
//                }
//
//                log.debug("2-"+i+"、第："+i+"条数据，对账结果");
//
//                if(bizType){
//                    bizBill.setDisposeType("1");
//                }else {
//                    bizBill.setDisposeType("0");
//                }
//                bizBill.setDisposeMessage(errorMessage);
//                addBizBillList.add(bizBill);
//
//                if(StringUtils.isNotEmpty(orderList)){
//                    try {
//                        String [] orderLists=orderList.split(",");
//                        if(orderLists!=null&&orderLists.length>0){
//                            for(String orde:orderLists){
//                                BizOrder order=new BizOrder();
//                                order.setDdId(orde);
//                                order.setPayMoney(String.valueOf((int)Double.parseDouble(l.getTotalFee()) * 100) );//
//                                order.setDdZftd("2");//设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
//                                ApiResponse<String>  res= oracleService.updateOrderPayTpye(order);
//                                BizOrder neBizOrder=new BizOrder();
//                                neBizOrder.setDdId(orde);
//                                if(res.isSuccess()) {
//                                    neBizOrder.setBillContrastMsg("该订单是由对账业务设置成支付成功的。");
//                                }else{
//                                    neBizOrder.setBillContrastMsg("该订单是由对账业务操作，对账业务修改订单状态时，修改失败 失败原因："+res.getMessage());
//                                }
//                                orderMapper.updateByPrimaryKeySelective(neBizOrder);
//                            }
//                        }
//                    }catch (Exception e){
//                    }
//                }
//            }
//        }
//
//        if(addBizBillList!=null && addBizBillList.size()>0){
//            billContrastMapper.insertBatch(addBizBillList);
//        }
//        log.debug(billDate+"对账业务数据处理结束");
//        return ret;
//    }

    /**
     * <pre>
     * 下载微信的对账单. 到账户表中
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
     * @param billDate   对账单日期 bill_date 下载对账单的日期，格式：20140603
     * @param billType   账单类型 bill_type ALL，返回当日所有订单信息，默认值，SUCCESS，返回当日成功支付的订单，REFUND，返回当日退款订单
     * @param tarType    压缩账单 tar_type 非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
     * @param deviceInfo 设备号 device_info 非必传参数，终端设备号
     * @param wxService   获取微信的
     * @return 保存到本地的临时文件
     */
    public ApiResponse<String> wxDownloadBill(String billDate){
//        1、检查对账日期是否正确
        //微信建议  请先检查当前商户号在指定日期内是否有成功的交易，如指定日期有交易则表示账单正在生成中，请在上午10点以后再下载。
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        try {
            LocalDate queryDate = LocalDate.parse(billDate,dateTimeFormatter);
            LocalDateTime queryDateTime = queryDate.atStartOfDay();
            if((queryDateTime.plusDays(1).plusHours(9).plusMinutes(59)).isAfter((LocalDateTime.now()))){
                System.out.println("您好，微信的对账文件需要在第二天的10点之后开始获取。其它时间不允许获取对账");
                return ApiResponse.fail("您好，微信的对账文件需要在第二天的10点之后开始获取。其它时间不允许获取对账");
            }
        }catch (DateTimeParseException e){
            return ApiResponse.fail("所传日期格式不对");
        }
//        2、检查账单表中是否存在已有的账单,如果有账单，就不从接口中获取
        SimpleCondition condition = new SimpleCondition(BizBillContrast.class);
        condition.eq(BizBillContrast.InnerColumn.tradeTime.name(), billDate);
        condition.eq(BizBillContrast.InnerColumn.patType.name(), "2");//支付通道(1、支付宝 2、微信)
        int i = billContrastMapper.selectCountByExample(condition);//
        if(i>0){
            return ApiResponse.success("当天已有账单数据，不需要再次从接口获取。");
        }
        ApiResponse<String> ret=new ApiResponse<String>();
//        3、从微信接口中获取账单信息
        WxPayBillResult billResult=null;
        try {
            billResult=wxService.downloadBill(billDate, "ALL", "", "");
        }catch (WxPayException e){
            //NO Bill Exist  3-1、当前商户没有成功的订单
            if(StringUtils.equals("no bill exist",StringUtils.lowerCase(e.getReturnMsg()))){
                return ApiResponse.success("获取微信对账时间："+billDate+"。获取失败，失败原因：当前商户号没有已成交的订单，不生成对账单");
            }else if(StringUtils.equals("no bill exist",StringUtils.lowerCase(e.getReturnMsg()))){
                return ApiResponse.fail("获取微信对账时间："+billDate+"。获取失败，失败原因：当前商户号没有已成交的订单或对账单尚未生成");
            }
            return ApiResponse.fail("获取微信对账时间："+billDate+"。获取失败，失败原因："+e.getReturnMsg());
        }catch (Exception e){
            e.printStackTrace();
            return ApiResponse.fail("获取微信对账时间："+billDate+"。获取失败，"+e.getMessage());
        }
        log.debug("1、开始处理时间为："+billDate+"的对账数据。共"+billResult.getTotalRecord()+"笔。总交易额:"+billResult.getTotalFee() );
        List<WxPayBillBaseResult> billResultList=billResult.getWxPayBillBaseResultLst();
        List<BizBillContrast> addBizBillList=new ArrayList<BizBillContrast>();
        if(billResult!=null&&billResult.getWxPayBillBaseResultLst().size()>0){
            for(WxPayBillBaseResult l:billResultList){
                BizBillContrast bizBill= new BizBillContrast();
                bizBill.setId(genId());
                bizBill.setTradeTime(billDate);
                bizBill.setOpenId(l.getOpenId());
                bizBill.setTradeType(l.getTradeType());//交易类型   JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里  MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
                //l.getTradeState()  SUCCESS，返回当日成功支付的订单    REFUND，返回当日退款订单 RECHARGE_REFUND，返回当日充值退款订单
                String tradeState="";
                if (StringUtils.equals(l.getTradeState(), "SUCCESS")) {//处理成功
                    tradeState="1";
                }else if (StringUtils.equals(l.getTradeState(), "REFUND")) {//返回当日退款订单
                    tradeState="2";
                }else if (StringUtils.equals(l.getTradeState(), "RECHARGE_REFUND")) {//返回当日充值退款订单
                    tradeState="2";
                }
                bizBill.setTradeState(tradeState);//交易状态
                bizBill.setTotalFee(l.getTotalFee());//总金额(元)
                bizBill.setRefundId(l.getRefundId());//微信退款单号
                bizBill.setRefundChannel(l.getRefundChannel());//退款类型
                bizBill.setPatType("2");//支付通道(1、支付宝 2、微信)
                bizBill.setOriginalMessage(l.toString());//对账文件的原始报文
                bizBill.setRefundFee(l.getSettlementRefundFee());//退款金额
                String transactionId=l.getTransactionId();//支付凭证ID
                bizBill.setTransactionId(transactionId);//支付凭证ID
                bizBill.setOrderId(l.getOutTradeNo());
                if(StringUtils.isEmpty(tradeState)){
                    bizBill.setDisposeMessage("该账单的-交易状态 类型没有处理，止条交易状态跳过处理");
                    bizBill.setDisposeType("0");//处理状态 0处理失败  1 成功
                }
                addBizBillList.add(bizBill);
            }
        }
//        4、批量插入账单表
        if(addBizBillList!=null && addBizBillList.size()>0){
            billContrastMapper.insertBatch(addBizBillList);
        }
        return ret;
    }

    /**
     * 对账业务处理
     * @param billDate  对账日期
     * @param handcraft  是否手工对账  如果是手工对账，就要删除 对账日期的账单
     * @param  payTpye  支付通道(ZDCLK0038 1、支付宝  2、微信  3、银联  4、快钱……)
     * @return
     */
    public ApiResponse<String> balanceBillAccount(String billDate,Boolean handcraft,String payTpye){

        BizTjDz bizTjDz=new BizTjDz();
        bizTjDz.setPkid(genId());
        bizTjDz.setDzSj(billDate);
        bizTjDz.setCreationTime(DateUtils.getNowTime());
        bizTjDz.setPayPass(payTpye);
        bizTjDzMapper.insertSelective(bizTjDz);
//        1、检查对账日期是否正确
        RuntimeCheck.ifTrue(StringUtils.isBlank(billDate), "对账时间不能为空");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate billDateOperate =null;
        try {
            billDateOperate = LocalDate.parse(billDate, dateTimeFormatter);
        }catch (DateTimeParseException e){
            return ApiResponse.fail("所传日期格式不对");
        }
//        2、判断是否为手工对账，如果是手工对账，需要清除上一次的对账记录
        if(handcraft){//
//            1、删除账单表的当前对账时间的数据
            billContrastMapper.delHandcraftBill(billDate);
//            2、将订单表的对账状态清除掉
            billContrastMapper.resettingOrderBillContrast(billDate);
        }
//        3、下载对账文件
        if(StringUtils.equals(payTpye,"2")){//支付通道(ZDCLK0038 1、支付宝  2、微信  3、银联  4、快钱……)
//        3-1、下载微信对账文件
            ApiResponse<String>  wxBill=this.wxDownloadBill(billDate);
            if(wxBill.getCode() != 200){
                RuntimeCheck.ifTrue(true, "获取微信对账文件失败："+wxBill.getMessage());
            }
        }else if(StringUtils.equals(payTpye,"1")){
//        3-2、下载其它对账文件  todo 后期增加
//            3-2-1获取账单的下地址
//            3-2-2通过下载地址，下载对账文件的zip 文件
//            3-2-3解压zip 文件，获取出对账文件
//            3-2-4找到出对账文件
//            3-2-5读取出账单信息


        }

//        4、对账业务开始
//        4-1-1、查询支付日期符合的订单
        List<BizOrder> orderList=billContrastMapper.getPayDateOrder(billDate);
//        4-1-2、查询支付日期符合的账单
        List<BizBillContrast> billContrastList=billContrastMapper.getPayDateBillContrast(billDate);
        //当订单为空，账单查询也为空时，就中止对账操作
        if((orderList==null&&orderList.size()==0)&&(billContrastList==null&&billContrastList.size()==0)){
            RuntimeCheck.ifTrue(true, "对账时间："+billDate+"。未找到支付订单。对账任务中止");
        }

//        4-2、拼装Redis集合
        SetOperations<String, String> redisGather=redisDao.opsForSet();
//        a.订单支付成功集合 orderSuccess;
//        b.订单退款成功集合 orderRefund;
//        c.订单待付款集合 orderUnpaid;
//        d.订单支付成功集合 billSuccess;
//        e.订单退款成功集合 billRefund;
        Double orderCountFell=0d;
        long orderCount=0;
//        4-2-1、将订单ID放入集合中
        if(orderList!=null&&orderList.size()>0){
            for(BizOrder order:orderList){

                //redisValue: 平台订单ID  支付通道（支付通道(ZDCLK0038 1、支付宝  2、微信  3、银联  4、快钱……)）     支付金额
                String redisKey="";
                String redisValue=order.getDdId()+","+order.getDdZftd()+","+Math.round(order.getDdZfje());
                String orderPayType=order.getDdZfzt();//支付状态（ZDCLK0039  0,待支付 1、支付成功  2、支付失败）
                if(StringUtils.equals(order.getDdZt(),"3")){
                    orderCountFell=orderCountFell+MathUtil.stringToDouble(order.getPayMoney());
                    redisKey="orderRefund";//退款
                    orderCount++;
//                    orderRefundList.add(redisKey,redisValue);
                }else if(StringUtils.equals(orderPayType,"0")){
//                    orderUnpaidList.add(redisKey,redisValue);
                    redisKey="orderUnpaid";//待支付
                }else if(StringUtils.equals(orderPayType,"1")){
                    orderCountFell=orderCountFell+MathUtil.stringToDouble(order.getPayMoney());
//                    orderSuccessList.add(redisKey,redisValue);
                    redisKey="orderSuccess";//支付成功
                    orderCount++;
                }
                if(StringUtils.isNotEmpty(redisKey)){
                    redisGather.add(redisKey,redisValue);
                }
            }
        }


        Double billFee=0d;
        long billCount=0;
//        4-2-2、将账单放入集合中
        if(billContrastList!=null&&billContrastList.size()>0){
            for(BizBillContrast bill:billContrastList){
                String redisKey="";
                //redisValue: 平台订单ID  支付通道（支付通道(ZDCLK0038 1、支付宝  2、微信)）     支付金额
                String redisValue=bill.getOrderId()+","+bill.getPatType();
                if(StringUtils.equals(bill.getTradeState(),"1")){//支付成功
                    String fee=bill.getTotalFee();
                    long payFee= (long) (MathUtil.stringToDouble(fee)*100);
                    billFee=billFee+payFee;
                    billCount++;
                    redisValue+=","+payFee;
                    redisKey="billSuccess";
                }else if(StringUtils.equals(bill.getTradeState(),"2")) {//支付成功
                    String fee=bill.getRefundFee();//退款金额
                    long payFee= (long) (MathUtil.stringToDouble(fee)*100);
                    billFee=billFee+payFee;
                    billCount++;
                    redisValue+=","+payFee;
                    redisKey="billRefund";
                }
                if(StringUtils.isNotEmpty(redisKey)){
                    redisGather.add(redisKey,redisValue);
                }
            }
        }
        bizTjDz.setBillCount(billCount+"");
        bizTjDz.setBillFee(String.valueOf((billFee/100)));

        //4-3、因为当有退款操作时，账单会有一正一负两条记录。所以当确定是退款操作时，需要将订单的正向记录移除
         redisGather.differenceAndStore("billSuccess", "billRefund","billSuccess");
//        4-5、对比操作开始
//      4-5-1、对比操作开始-订单待支付和账单支付成功的交集，这个交集是可能没有接收到回调接口，需要补充。
        Set<String> unpaidOracleId=redisGather.intersect("orderUnpaid","billSuccess");
        if(unpaidOracleId!=null&&unpaidOracleId.size()>0){
            for(String unpaid:unpaidOracleId){
                try {
                    String[] obj=unpaid.split(",");
                    BizOrder order=new BizOrder();
                    order.setDdId(obj[0]);
                    order.setPayMoney(obj[2]);//
                    order.setDdZftd(obj[1]);//设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
                    ApiResponse<String>  res= oracleService.updateOrderPayTpye(order);
                    BizOrder neBizOrder=new BizOrder();
                    neBizOrder.setDdId(obj[0]);
                    if(res.isSuccess()) {
                        neBizOrder.setBillContrastMsg("该订单是由对账业务设置成支付成功的。");
                    }else{
                        neBizOrder.setBillContrastMsg("该订单是由对账业务操作，对账业务修改订单状态时，修改失败 失败原因："+res.getMessage());
                    }

                    neBizOrder.setDdZfsj(billDateOperate.format(formatters)+" 00:00:00");
                    orderMapper.updateByPrimaryKeySelective(neBizOrder);
                    orderCount++;
                    orderCountFell=orderCountFell+MathUtil.stringToDouble(order.getPayMoney());
                    redisGather.add("orderSuccess",unpaid);
                }catch (Exception e){}
            }
        }

        bizTjDz.setOracleCount(orderCount+"");
        bizTjDz.setOracleFee(String.valueOf((orderCountFell/100)));

//      4-5-2、对比操作开始-支付成功和退款业务的交集，这两个交集是对账成功。
        Set<String> successOracleId=redisGather.intersect("orderSuccess","billSuccess");
        Set<String> refundOracleId=redisGather.intersect("orderRefund","billRefund");
        List<String> successOrderList=new ArrayList<String>();
        Double successFee=0d;
        if(successOracleId!=null&&successOracleId.size()>0){
            for(String success:successOracleId){
                try {
                    String[] obj=success.split(",");
                    successOrderList.add(obj[0]);
                    successFee=successFee+MathUtil.stringToDouble(obj[2]);
                }catch (Exception e){}
            }
        }
        bizTjDz.setSuccessCount(successOracleId.size()+"");
        bizTjDz.setSuccessFee(String.valueOf((successFee/100)));

        Double successRefundFee=0d;
        if(refundOracleId!=null&&refundOracleId.size()>0){
            for(String refund:refundOracleId){
                try {
                    String[] obj=refund.split(",");
                    successOrderList.add(obj[0]);
                    successRefundFee = successRefundFee+MathUtil.stringToDouble(obj[2]);
                }catch (Exception e){}
            }
        }
        bizTjDz.setSuccessRefundCount(refundOracleId.size()+"");
        bizTjDz.setSuccessRefundFee(String.valueOf((successRefundFee/100)));

//      4-5-2-1、更新订单表的对账字段为成功。
        if(successOrderList!=null&& successOrderList.size()>0){
            billContrastMapper.updateOrderContrastType(successOrderList);
        }

//      4-5-2-2、更新账单表的对账字段为成功。
        if(successOrderList!=null&&successOrderList.size()>0){
            billContrastMapper.updateBillContrastType(successOrderList);
        }
        billContrastMapper.updateNotBillContrastType(billDate);
        billContrastMapper.updateNotOrderContrastType(billDate);

//        bizTjDzMapper.addStatistics(bizTjDz,billDate);
        bizTjDzMapper.updateStatistics(bizTjDz,billDate);

//        4-6、清除redis所有k
        redisDao.delete("orderSuccess");
        redisDao.delete("orderRefund");
        redisDao.delete("orderUnpaid");
        redisDao.delete("billSuccess");
        redisDao.delete("billRefund");

        return ApiResponse.success();
    }

    /**
     * 提现统计接口
     * 1、订单统计
     * 2、提现统计
     * @param tjsj
     */
    public void orderStatistics(String tjsj){
        BizTj tj=new BizTj();
        tj.setTjSj(tjsj);
        tjMapper.delete(tj);

        tj.setCjsj(DateUtils.getNowTime());
        tjMapper.orderStatistics(tjsj,DateUtils.getNowTime());


    }
}
