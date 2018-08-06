package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizTxJlMapper;
import com.cwb.platform.biz.mapper.BizTxMapper;
import com.cwb.platform.biz.model.*;
import com.cwb.platform.biz.service.*;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysZdxm;
import com.cwb.platform.sys.service.ZdxmService;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.ExcelUtil;
import com.cwb.platform.util.commonUtil.MathUtil;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TxServiceImpl extends BaseServiceImpl<BizTx,java.lang.String> implements TxService{

    @Resource(name = "wxPayService")
    private WxPayService wxService;
    @Autowired
    private BizTxJlMapper txJlMapper;

    @Autowired
    private BizTxMapper entityMapper;

    @Autowired
    private YjmxService yjmxService;

    @Autowired
    private ZhService zhService;
    @Autowired
    private YhkService yhkService;
    @Autowired
    private ZdxmService zdxmService;

    @Autowired
    private PtyhService ptyhService;


    @Value("${staticPath}")
    private String staticPath;

    @Value("${wechat.pay.appId}")
    private String appid;
    @Value("${wechat.pay.mchId}")
    private String mchId;
    @Value("${wx_checkname}")
    private String wxCheckName;

    @Value("${spbill_create_ip}")
    private String spbillCreateIp;

    @Override
    public List<String> getSpecialCols() {
        return Arrays.asList("ttFs","ttJe","ttZt","ttShzt");
    }



    @Override
    public List<Map<String, String>> getSpecialVals(List<BizTx> list) {
        List<Map<String,SysZdxm>> mapList = new ArrayList<>(3);
        String[] codes = new String[]{"ZDCLK0047","ZDCLK0048","ZDCLK0049"};
        for (String code : codes) {
            List<SysZdxm> txFsList = zdxmService.findEq(SysZdxm.InnerColumn.zdlmdm,code);
            RuntimeCheck.ifEmpty(txFsList,"未找到字典");
            Map<String,SysZdxm> map = txFsList.stream().collect(Collectors.toMap(SysZdxm::getZddm,p->p));
            mapList.add(map);
        }

        List<Map<String,String>> data = new ArrayList<>();
        for (BizTx tx : list) {
            Map<String,String> map = new HashMap<>();
            SysZdxm txFs = mapList.get(0).get(tx.getTtFs());
            SysZdxm txZt = mapList.get(1).get(tx.getTtZt());
            SysZdxm txShZt = mapList.get(2).get(tx.getTtShzt());
            Double txJe = MathUtil.div(tx.getTtJe(),100);

            map.put("ttFs",txFs == null ? "" : (txFs.getZdmc() == null ? "-" : txFs.getZdmc()));
            map.put("ttZt",txZt == null ? "" : (txZt.getZdmc() == null ? "-" : txZt.getZdmc()));
            map.put("ttShzt",txShZt == null ? "" : (txShZt.getZdmc() == null ? "-" : txShZt.getZdmc()));
            map.put("ttJe",txJe.toString());
            data.add(map);
        }
        return data;
    }

    @Override
    protected Mapper<BizTx> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizTx.class;
    }

    /**
     * 分页补充
     * @param condition
     * @return
     */
    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        condition.setOrderByClause("TT_SJ desc");
        return true;
    }

    /**
     * 更新审核状态
     * @param bizTx
     * @return
     */
    @Override
    public ApiResponse<String> updateShzt(BizTx bizTx) {
        RuntimeCheck.ifBlank(bizTx.getId(),"Id不能为空");
        RuntimeCheck.ifBlank(bizTx.getTtShzt(), "审核状态不能为空");
        if(StringUtils.containsNone(bizTx.getTtShzt(), new char[]{'1', '2'})){
            return ApiResponse.fail("请输入正确的属性");
        }
        BizTx tx = findById(bizTx.getId());
        RuntimeCheck.ifTrue(ObjectUtils.isEmpty(tx),"根据主键id无法查询到该提现明细");
        RuntimeCheck.ifTrue(StringUtils.equals(tx.getTtShzt(),"1"),"该记录已经审核通过");//0、 待审核 1、 审核通过 2、 审核拒绝)
        RuntimeCheck.ifTrue(StringUtils.equals(tx.getTtShzt(),"2"),"该记录已经被驳回");
//        RuntimeCheck.ifTrue(StringUtils.containsNone(tx.getTtFs(),"2"),"提现方式为人工转账才能审核");

        BizTx newBizTx=new BizTx();
        newBizTx.setId(bizTx.getId());//订单ID
        newBizTx.setTtShzt(bizTx.getTtShzt());//提现审核状态(0、待审核 1、审核通过 2、审核拒绝)
        newBizTx.setTtBz(bizTx.getTtBz());//审核描述
        if(StringUtils.equals(bizTx.getTtShzt(),"2")){
            newBizTx.setTtZt("4");//设置提现状态 ZDCLK0048 (0 待审核 1、 已收取 2、 已经发送  3、 过期未收取 4、 无效申请)
        }

        int i = update(newBizTx);

        BizYjmx bizYjmx=new BizYjmx();
        bizYjmx.setId(tx.getYjId());
        //提现审核拒绝时，明细表中的申请也要是失败的
        if(StringUtils.equals(bizTx.getTtShzt(),"2")){
            bizYjmx.setZjZt("2");//设置提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 2、提现失败) 提现操作默认0 佣金操作默认1
            bizYjmx.setTxShZt("2");//提现审核状态 该字段仅限提现业务 与biz_tx表中(TT_SHZT 提现审核状态)的审核字段同步   ZDCLK0049 (0、 待收取 1、 审核通过 2、 审核拒绝)

        }else {
            bizYjmx.setTxShZt("1");
        }
        bizYjmx.setZjBz(bizTx.getTtBz());
        // 更新佣金明细表
        yjmxService.update(bizYjmx);

        //提现拒绝后，要将冻结的钱，返还给用户
        if(StringUtils.equals(bizYjmx.getTxShZt(),"2")){
            // 更新账户表
            List<String> userList=new ArrayList<String>();
            userList.add(tx.getYhId());
            zhService.userAccountUpdate(userList);
        }
        return i == 1 ? ApiResponse.success():ApiResponse.fail();
    }

    /**
     * 更新提现状态
     * @param bizTx
     * @return
     */
    @Override
    public ApiResponse<String> updateTxzt(BizTx bizTx) {
        RuntimeCheck.ifBlank(bizTx.getId(),"Id不能为空");
        RuntimeCheck.ifBlank(bizTx.getTtZt(),"提现状态不能为空");//获取提现状态 ZDCLK0048 (0 待收取 1、 已收取 2、 已经发送  3、 过期未收取 4、 无效申请)
        if(StringUtils.containsNone(bizTx.getTtZt(), new char[]{'1', '4'})){
            return ApiResponse.fail("请输入正确的提现状态属性");
        }
        RuntimeCheck.ifBlank(bizTx.getTtBz(),"备注不能为空");

        // 提现明细
        BizTx tx = findById(bizTx.getId());
        RuntimeCheck.ifTrue(ObjectUtils.isEmpty(tx),"根据主键id无法查询到该提现明细");
        RuntimeCheck.ifTrue(StringUtils.equals(tx.getTtZt(),bizTx.getTtZt()),"修改提现状态不能与当前状态相同");
        RuntimeCheck.ifTrue(StringUtils.containsNone(tx.getTtShzt(),"1"),"该记录审核未通过或被驳回");
        // 佣金明细
        BizYjmx bizYjmx = yjmxService.findById(tx.getYjId());
        RuntimeCheck.ifTrue(ObjectUtils.isEmpty(bizYjmx),"根据佣金明细id无法查询到佣金明细");
        RuntimeCheck.ifFalse(StringUtils.equals(bizYjmx.getZjFs(),"-1"),"必须是提现才能修改提现状态");


        if(StringUtils.equals(bizTx.getTtZt(),"1")){
            bizYjmx.setZjZt("1");
        }else{
            bizYjmx.setZjZt("2");
        }
        // 更新佣金明细表
        bizYjmx.setZjBz(bizTx.getTtBz());

        BizTx updateBizTx=new BizTx();
        updateBizTx.setId(bizTx.getId());
        updateBizTx.setTxXm(bizTx.getTxXm());
        updateBizTx.setTtZt(bizTx.getTtZt());
        updateBizTx.setTtBz(bizTx.getTtBz());
        // 更新提现明细表
        update(updateBizTx);
        // 更新佣金明细表
        yjmxService.update(bizYjmx);

        // 更新账户表
        List<String> userList=new ArrayList<String>();
        userList.add(tx.getYhId());
        zhService.userAccountUpdate(userList);

        return ApiResponse.success();
    }


    /**
     * 提现ID
     * @param bizTx
   *                  提现ID
     * @return
     */
    @Override
    public ApiResponse<String> wxEnterprisePay(BizTx bizTx) {
        //获取数据
        String txId=bizTx.getId();//提现ID
        txId=StringUtils.trim(txId);
        List<BizTx> txList=new ArrayList<BizTx>();
        if(StringUtils.isEmpty(txId)){
            BizTx whereEntity=new BizTx();
            whereEntity.setTtFs("1");//设置提现方式  1、微信红包
            whereEntity.setTtZt("0");//提现状态 0 待提出
            whereEntity.setTtShzt("1");//设置提现审核状态  0-待审核
            txList=findByEntity(whereEntity);
        }else{
            BizTx find=findById(txId);
            if(find!=null){
                txList.add(find);
            }
        }

        String ensembleFruit="2";//1支付成功  2支付失败
        String ensembleFruitDesc="";//提现操作的记录
        int fruitCount=0;//记录成功的笔数
        //遍历提现列表
        if(txList!=null&&txList.size()>0){
            for(BizTx tx:txList){
                String fruit="2";//1支付成功  2支付失败
                String fruitDesc="";//提现操作的记录
                BizTxJl txJl=new BizTxJl();//提现记录表
                txJl.setPkId(genId());
                txJl.setCjsj(DateUtils.getNowTime());//创建时间
                txJl.setTxId(tx.getId());
                txJl.setYhId(tx.getYhId());

                String ttFs=tx.getTtFs();//提现方式 ZDCLK0047  1、微信提现  2、银行卡提现 3、人工提现
                Double ttJe=tx.getTtJe();//提现金额(分)
                String ttZt=tx.getTtZt();//提现状态 0 待收取 1、 已收取 2、 已经发送  3、 过期未收取 4、 无效申请
                String ttShzt=tx.getTtShzt();//提现审核状态 ZDCLK0049 (0、 待审核 1、 审核通过 2、 审核拒绝)

                txJl.setTxMoney(String.valueOf(ttJe));//提现金额
                txJl.setTxType(ttFs);//提现方式
                if(StringUtils.equals(ttShzt,"1")){//提现审核状态 ZDCLK0049 (0、 待审核 1、 审核通过 2、 审核拒绝)
                    if(StringUtils.equals(ttFs,"1")){
                        if(StringUtils.equals(ttZt,"0")){//提现状态为 待打款的申请才能进行操作
                            BizPtyh user=  ptyhService.findById(tx.getYhId());//获取用户信息
                            int payMoney=(int) Math.ceil(ttJe);
                            if(ttJe-payMoney==0){
                                ApiResponse<String> makeMoney = wxEnterprisePayRealize(tx.getId(),user.getYhOpenId(),payMoney,user.getYhXm(),"平台系统自动打款");
                                if(makeMoney.isSuccess()){//打款成功
                                    fruitCount++;
                                    String paymentNo=makeMoney.getMessage();//微信返回的支付订单号
                                    fruit="1";//1支付成功  2支付失败
                                    fruitDesc="打款成功";//微信打款成功
                                    txJl.setWxPaymentNo(paymentNo);//只有成功后，就会有这一条记录

                                    BizTx bizTx1=new BizTx();
                                    bizTx1.setId(tx.getId());
                                    bizTx1.setTtZt("1");//设置提现状态 ZDCLK0048 (0 待审核 1、 已收取 2、 已经发送  3、 过期未收取 4、 无效申请)
                                    bizTx1.setTtBz("平台系统自动打款。提现记录ID="+txJl.getPkId());//
                                    ApiResponse<String> updTx=null;
                                    try {
                                        txJl.setFruit(fruit);
                                        txJl.setTxDesc(fruitDesc);
                                        txJlMapper.insert(txJl);
                                        updTx=this.updateTxzt(bizTx1);
                                    }catch (Exception e){}
                                    if(updTx!=null){

                                    }
                                }else{
                                    fruitDesc=makeMoney.getMessage();
                                }
                            }else{
                                fruitDesc="该提现申请中存在小数，系统提现仅能精确到分，此订单无法自动打款！";
                            }

                        }else{
                            fruitDesc="该提现申请不处于待打款状态，不能进行自动打款操作！";
                        }
                    }else{
                        fruitDesc+="暂未开通其它的提款申请！";
                    }
                }else{
                    fruitDesc="该提现申请还未审核通过，跳过此业务！";
                }
                if(StringUtils.equals(fruit,"2")){
                    txJl.setFruit(fruit);
                    txJl.setTxDesc(fruitDesc);
                    txJlMapper.insert(txJl);
                }

                ensembleFruit=fruit;
                ensembleFruitDesc=fruitDesc;
            }
        }
        if(StringUtils.isEmpty(bizTx.getId())){
            return ApiResponse.success("共"+txList.size()+"笔。成功了："+fruitCount+"笔。失败"+(txList.size()-fruitCount));
        }else{
            if(StringUtils.equals(ensembleFruit,"2")){
                return ApiResponse.fail(ensembleFruitDesc);
            }else {
                return ApiResponse.success("支付成功");
            }
        }
    }

    /**
     * 微信提现操作 实现
     * @param orderId  支付的ID号，这里给的是提现表ID，因为同一个订单如果重复了，
     * @param openID   付款的用户的微信OPEN_ID
     * @param amount   支付的金额 单位是分
     * @param userName   用户的真实姓名
     * @return
     */
    public ApiResponse<String> wxEnterprisePayRealize(String orderId,String openID,int amount,String userName,String desc){
        if (StringUtils.isBlank(orderId)){
            return ApiResponse.fail("订单ID不能为空");
        }
        if (StringUtils.isBlank(openID)){
            return ApiResponse.fail("用户的OPEN_ID不能为空");
        }
        if (amount>1*100&&amount<20000*100){
            return ApiResponse.fail("微信提现仅支付1到20000元的付款，该金额已经超出限额");
        }
        if(StringUtils.equals(wxCheckName,"FORCE_CHECK")&&StringUtils.isEmpty(userName)){
            return ApiResponse.fail("目前系统强制校验真实姓名，所以用户姓名不能为空");
        }

        EntPayRequest request=new EntPayRequest();
        request.setMchAppid(appid);
        request.setMchId(mchId);

        request.setPartnerTradeNo(orderId);//订单ID
        request.setOpenid(openID);//用户的open_id  赵虎
        request.setCheckName(wxCheckName);//校验用户姓名选项  FORCE_CHECK 强校验真实姓名  NO_CHECK：不校验真实姓名 
        request.setReUserName(userName);//收款用户姓名
        request.setAmount(amount);//金额
        request.setDescription("公司付款Test");
        request.setSpbillCreateIp(spbillCreateIp);//Ip地址   27.16.192.155
        String paymentNo="";//微信返回的支付订单号
        try {
            EntPayResult wxPayReturn = wxService.getEntPayService().entPay(request);
            paymentNo=wxPayReturn.getPaymentNo();
        } catch (WxPayException e) {
            e.printStackTrace();
            e.getMessage();
            e.getErrCodeDes();
            return ApiResponse.fail("微信打款失败："+e.getMessage());
        }
        return ApiResponse.success(paymentNo);
    }



    //==============================================================APP端  开始===========

    /**
     * 用户提现
     * @param ttje  金额
     * @param user
     * @param ttfs
     * @return
     */
    public ApiResponse<String> saveUserDraw(Double ttje, String yhkid, BizYhk bizYhk, BizPtyh user, String ttfs){
        BizTx newEntity=new BizTx();

        String userId=user.getId();//获取用户
        BizZh userZh=zhService.findById(userId);
        RuntimeCheck.ifFalse(userZh != null && userZh.getYhZhye() >= ttje,"提现金额不能大于余额");
        if(StringUtils.equals(ttfs,"2")){
            String yhkh=bizYhk.getYhkKh();//银行卡号不能为空
            RuntimeCheck.ifBlank(yhkh, "银行卡号不能为空");
            newEntity.setTtYhkh(yhkh);
            newEntity.setTtKhh(bizYhk.getYhkKhh());//设置用户开户行
            newEntity.setTxXm(bizYhk.getYhkXm());//提现姓名
            newEntity.setYhkid(bizYhk.getId());
            newEntity.setTtSsyh(bizYhk.getYhkSsyh());//提现所属银行
        }

        String yjid=genId();
        newEntity.setTtFs(ttfs);
        newEntity.setId(genId());
        newEntity.setYhId(userId);
        newEntity.setYhMc(user.getYhXm());
        newEntity.setTtJe(ttje);
        newEntity.setTtZt("0");
        newEntity.setTtSj(DateUtils.getNowTime());
        newEntity.setTtShzt("0");
        newEntity.setYjId(yjid);//流水表id


       int i= entityMapper.insert(newEntity);
       if(i==1){
           //插入流水表
            BizYjmx newBizYjmx=new BizYjmx();
            newBizYjmx.setId(yjid);
            newBizYjmx.setYhId(userId);
            newBizYjmx.setZjJe(ttje);
            newBizYjmx.setZjFs("-1");//费用方式 ZDCLK0053 (1 佣金 -1 提现)
            newBizYjmx.setCjsj(DateUtils.getNowTime());
            newBizYjmx.setZjZt("0");//提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 ) 提现操作默认0 佣金操作默认1
            newBizYjmx.setMxlx("4");//明细类型  ZDCLK0066 1、付款 2、分佣 3、消费 4、提现
            newBizYjmx.setTxShZt(newEntity.getTtShzt());
           yjmxService.save(newBizYjmx);
       }
        // 更新账户表
        List<String> userList=new ArrayList<String>();

        if(org.apache.commons.lang.StringUtils.isNotEmpty(userId)){
            userList.add(userId);
        }
        zhService.userAccountUpdate(userList);

        if(StringUtils.equals(ttfs,"2")){
            //更新银行卡使用时间。
            BizYhk updateYhkDate=new BizYhk();
            updateYhkDate.setYhId(bizYhk.getId());
            updateYhkDate.setYhkScsysj(DateUtils.getNowTime());
            yhkService.update(updateYhkDate);
        }

        return ApiResponse.success();
    }

    @Override
    public ApiResponse<List<String>> batchImport(String filePath) {
        ApiResponse<List<String>> result = new ApiResponse<>();
        List<List<String>> data = ExcelUtil.getData(staticPath+filePath);

        List<String> resp  = new ArrayList<>();

        if (CollectionUtils.size(data) < 2){
            result.setMessage("文件格式有误，请重新上传");
            result.setCode(100);
            return result;
        }

//        List<String> head = data.get(0);

//        List<BizTx> txList = new ArrayList<>(data.size());
        data = data.subList(1,data.size());
        String now = DateUtils.getNowTime(); // 当前时间 2018-06-25 00:00:00

        int row = 2;  // 初始行为 2
        int total = data.size();
        int success = 0;
        int fail = 0;

        for (List<String> d : data) {
            String id = d.get(0); // 提现id
            String zt = d.get(1); // 提现状态
            String bz =d.get(2); // 备注
//            String yhkhh = d.get(3); //开户行
            BizTx tx = new BizTx();

            tx.setId(id);
            tx.setTtZt(zt);
            tx.setTtBz(bz);

//            tx.setYhMc(yhmc);
//            tx.setTtJe(MathUtil.mul(new Double(txje),100));
//            tx.setId(genId());
//            tx.setTtSj(now);
//            tx.setTtYhkh(yhkh);
//            tx.setTtKhh(yhkhh);
            ApiResponse<String> response = updateTxzt(tx);
            if(response.getCode() == 200){  // 更新状态成功
                success++;
            }else{
                fail ++;
                resp.add("第" + row + "行 ： " + response.getMessage());
            }
            row++;
            result.setResult(resp);
//            entityMapper.insertSelective(tx);
        }
        result.setMessage("总共" + total + "行 ， 成功总数 ：" + success + " , 失败总数 ：" + fail);
        return result;
    }



}
