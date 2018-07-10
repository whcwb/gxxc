package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizBillContrastMapper;
import com.cwb.platform.biz.mapper.BizOrderMapper;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.model.BizBillContrast;
import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.*;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.MathUtil;
import com.github.binarywang.wxpay.bean.result.WxPayBillBaseResult;
import com.github.binarywang.wxpay.bean.result.WxPayBillResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.List;
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
    private PtyhService pyhtService;

    @Autowired
    private StringRedisTemplate redisDao;

    @Autowired
    private YjmxService yjmxService;

    @Autowired
    private BizPtyhMapper userMapper;

    @Autowired
    private CpService cpService;

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

//                        //插入两条支付信息插入流水表
//                        BizYjmx newBizYjmx1 = new BizYjmx();
//                        newBizYjmx1.setId(genId());
//                        newBizYjmx1.setZjId(l.getDdId());
//                        newBizYjmx1.setYhId(l.getYhId());//消费的用户
//                        newBizYjmx1.setZjJe(MathUtil.stringToDouble( l.getPayMoney()));//支付的金额
//                        newBizYjmx1.setZjFs("1");//费用方式 ZDCLK0053 (1 佣金 -1 提现)
//                        newBizYjmx1.setCjsj(DateUtils.getNowTime());
//                        newBizYjmx1.setZjZt("1");//提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 ) 提现操作默认0 佣金操作默认1
//                        newBizYjmx1.setMxlx("1");//明细类型  ZDCLK0066 1、付款 2、分佣 3、消费 4、提现
//                        yjmxService.save(newBizYjmx1);
//
//                        newBizYjmx1.setId(genId());
//                        newBizYjmx1.setZjFs("-1");//费用方式 ZDCLK0053 (1 佣金 -1 提现)
//                        newBizYjmx1.setMxlx("3");//明细类型  ZDCLK0066 1、付款 2、分佣 3、消费 4、提现
//                        yjmxService.save(newBizYjmx1);
//
//                        // 判断订单产品是否属于学费，只有学费才生成邀请码
//                        if(StringUtils.equals(bizCp.getCpType(),"1")) { // 产品类型为学费时 ， 需要生成邀请码
//                            BizPtyh bizPtyh=pyhtService.findById(l.getYhId());
//                            if(StringUtils.isEmpty(bizPtyh.getYhZsyqm())){//如果该用户已有邀请码，就不再给该用户创建邀请码
//                                String yhZsyqm = genId();
//
//                                File logoFile = new File(logoFileUrl);
//                                String yhZsyqmImg = "QRCode/"+DateUtils.getToday("yyyyMMdd")+"/";
//
//                                File file=new File(qrCodeFileUrl + yhZsyqmImg);
//                                if (!file.exists()  && !file.isDirectory()){
//                                    file.mkdirs();
//                                }
//
//                                String note = "您的好友：" + l.getYhXm() + " 邀请您";
//                                note="";//经理说，生成的图片不需要增加文件。所以这行去掉
//                                ZXingCode.drawLogoQRCode(logoFile, new File(qrCodeFileUrl + yhZsyqmImg+yhZsyqm + ".png"), yhZsyqm, note);
//                                log.debug("3、用户：" + l.getYhXm() + "。生成邀请码成功");
//
//                                BizPtyh user = new BizPtyh();
//                                user.setId(l.getYhId());
//                                user.setYhZsyqm(yhZsyqm);//用户自己邀请码
//                                user.setYhZsyqmImg("/"+yhZsyqmImg+yhZsyqm + ".png");//用户自己邀请码
//                                userMapper.updateByPrimaryKeySelective(user);
//
//                            }
//                        }

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

    /**
     * 系统对账处理
     * 1、
     * @param billResult
     * @return
     */
    public List<String> billContrast(WxPayBillResult billResult,String billDate){
        List<String> ret=new ArrayList<String>();
        log.debug("1、开始处理时间为："+billDate+"的对账数据。共"+billResult.getTotalRecord()+"笔。总交易额:"+billResult.getTotalFee() );
        List<WxPayBillBaseResult> billResultList=billResult.getWxPayBillBaseResultLst();
        List<BizBillContrast> addBizBillList=new ArrayList<BizBillContrast>();
        String orderList="";//待缴费状态的订单
        if(billResultList!=null && billResultList.size()>0){
            int i=1;
            for(WxPayBillBaseResult l:billResultList){
                BizBillContrast bizBill= new BizBillContrast();
                bizBill.setId(genId());
                bizBill.setTradeTime(billDate);
                bizBill.setOpenId(l.getOpenId());
                bizBill.setTradeType(l.getTradeType());//交易类型   JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里  MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
                bizBill.setTradeState(l.getTradeState());//交易状态
                bizBill.setTotalFee(l.getTotalFee());//总金额(元)
                bizBill.setRefundId(l.getRefundId());//微信退款单号
                bizBill.setRefundChannel(l.getRefundChannel());//退款类型
                bizBill.setPatType("2");//支付通道(1、支付宝 2、微信)
                bizBill.setOriginalMessage(l.toString());//对账文件的原始报文
                bizBill.setRefundFee(l.getSettlementRefundFee());//退款金额
                bizBill.setOrderId(l.getOutTradeNo());

                Boolean bizType=true;
                String errorMessage="操作成功";
                log.debug("2-"+i+"、开始处理第："+i+"条数据");
                log.debug("2-"+i+"、对账的文件："+l.toString());
                String transactionId=l.getTransactionId();
                String orderId=l.getOutTradeNo();//

                BizOrder newBizOrder=new BizOrder();
                newBizOrder.setDdId(orderId);

                SimpleCondition condition = new SimpleCondition(BizOrder.class);
                condition.eq(BizOrder.InnerColumn.ddZfpz.name(), transactionId);//支付状态（0,待支付 1、支付成功  2、支付失败）
                condition.eq(BizOrder.InnerColumn.ddId.name(), orderId);//定时任务处理状态(0、待处理 1、处理成功 2、处理失败 )
                List<BizOrder> list = orderMapper.selectByExample(condition);//
                if (list == null || list.isEmpty()) {
                    bizType=false;
                    errorMessage="微信支付流水ID："+transactionId+"未找到这笔订单";
                    log.debug("2-"+i+"、对账的文件："+l.toString());
                }
                if(bizType) {
                    BizOrder order = list.get(0);
                    String billContrastType = order.getBillContrastType();//对账状态：0未对账      1、已对账      2、对账异常
//                    对账成功的，就跳过处理
                    if (StringUtils.equals(billContrastType, "1")) {
                        bizType = false;
                        errorMessage = "微信支付流水ID：" + transactionId + " 该订单已经对账成功 ，不需要再次对账处理";
                        log.debug("2-" + i + "、" + errorMessage);
                        newBizOrder.setBillContrastType("2");
                        newBizOrder.setBillContrastMsg(errorMessage);
                        orderMapper.updateByPrimaryKeySelective(newBizOrder);
                    } else {
                        //交易类型   JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里  MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
                        String tradeType = l.getTradeType();
                        //交易状态
                        //                SUCCESS: 对应收入 REFUND: 对应支出-退款 REVOKED: 对应支出-撤销
                        //                这里有几点注意：
                        //                1、账单的交易状态，和订单的交易状态没有关系，发生过退款的订单，在这里依然有一条success
                        //                交易状态不会变更 2、只有发生过支付（进账）的订单，在撤销后才会发生支出并出现在对账单里。
                        String tradeState = l.getTradeState();
                        String totalFee = l.getTotalFee();//交易金额
                        String settlementRefundFee = l.getSettlementRefundFee();//退款金额
                        if (StringUtils.equals(tradeState, "SUCCESS")) {//处理成功
                            if (MathUtil.stringToDouble(totalFee) - order.getDdZfje() / 100 != 0) {
                                bizType = false;
                                errorMessage = "微信支付流水ID：" + transactionId + " 金额与订单中金额(" + (order.getDdZfje() / 100) + ")不匹配";
                                log.debug("2-" + i + "、对账的文件：" + l.toString());
                                newBizOrder.setBillContrastType("2");
                                newBizOrder.setBillContrastMsg(errorMessage);
                                orderMapper.updateByPrimaryKeySelective(newBizOrder);
                            } else {
                                String ddZt = order.getDdZt();//获取订单状态(ZDCLK0037 1、待缴费 2、已缴费 3、已退费)
                                if (StringUtils.equals(ddZt, "1") && StringUtils.equals(bizBill.getRefundFee(), "0.00")) {
                                    orderList += order.getDdId() + ",";
                                }
                                //修改订单表
                                newBizOrder.setBillContrastType("1");
                                newBizOrder.setBillContrastMsg("");
                                orderMapper.updateByPrimaryKeySelective(newBizOrder);
                            }
                        } else if (StringUtils.equals(tradeState, "REVOKED") || StringUtils.equals(tradeState, "REFUND")) {//撤销
                            //后台退费后，这里会有撤销操作。
                            if (StringUtils.isNotEmpty(orderList)) {
                                orderList = orderList.replaceAll(order.getDdId() + ",", "");
                            }
                            errorMessage = "微信支付流水ID：" + transactionId + " 于" + l.getTradeTime() + "产生了退款操作。请紧急联系管理人员进行核实";
                            log.debug("2-" + i + "、" + errorMessage);
                            newBizOrder.setBillContrastType("2");
                            newBizOrder.setBillContrastMsg(errorMessage);
                            orderMapper.updateByPrimaryKeySelective(newBizOrder);
                        }

                    }
                }

                log.debug("2-"+i+"、第："+i+"条数据，对账结果");

                if(bizType){
                    bizBill.setDisposeType("1");
                }else {
                    bizBill.setDisposeType("0");
                }
                bizBill.setDisposeMessage(errorMessage);
                addBizBillList.add(bizBill);

                if(StringUtils.isNotEmpty(orderList)){
                    try {
                        String [] orderLists=orderList.split(",");
                        if(orderLists!=null&&orderLists.length>0){
                            for(String orde:orderLists){
                                BizOrder order=new BizOrder();
                                order.setDdId(orde);
                                order.setPayMoney(String.valueOf((int)Double.parseDouble(l.getTotalFee()) * 100) );//支付宝，实际支付的金额
                                order.setDdZftd("2");//设置支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
                                ApiResponse<String>  res= oracleService.updateOrderPayTpye(order);
                                BizOrder neBizOrder=new BizOrder();
                                neBizOrder.setDdId(orde);
                                if(res.isSuccess()) {
                                    neBizOrder.setBillContrastMsg("该订单是由对账业务设置成支付成功的。");
                                }else{
                                    neBizOrder.setBillContrastMsg("该订单是由对账业务操作，对账业务修改订单状态时，修改失败 失败原因："+res.getMessage());
                                }
                                orderMapper.updateByPrimaryKeySelective(neBizOrder);
                            }
                        }
                    }catch (Exception e){
                    }
                }
            }
        }

        if(addBizBillList!=null && addBizBillList.size()>0){
            billContrastMapper.insertBatch(addBizBillList);
        }
        log.debug(billDate+"对账业务数据处理结束");
        return ret;
    }
}
