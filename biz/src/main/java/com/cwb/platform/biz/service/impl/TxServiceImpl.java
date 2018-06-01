package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizTxMapper;
import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.model.BizZh;
import com.cwb.platform.biz.service.TxService;
import com.cwb.platform.biz.service.YjmxService;
import com.cwb.platform.biz.service.ZhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class TxServiceImpl extends BaseServiceImpl<BizTx,java.lang.String> implements TxService{
    @Autowired
    private BizTxMapper entityMapper;

    @Autowired
    private YjmxService yjmxService;

    @Autowired
    private ZhService zhService;




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
        RuntimeCheck.ifTrue(StringUtils.equals(tx.getTtShzt(),"1"),"该记录已经审核通过");
        RuntimeCheck.ifTrue(StringUtils.equals(tx.getTtShzt(),"2"),"该记录已经被驳回");
        RuntimeCheck.ifTrue(StringUtils.containsNone(tx.getTtFs(),"2"),"提现方式为人工转账才能审核");

        BizTx newBizTx=new BizTx();
        newBizTx.setId(bizTx.getId());//订单ID
        newBizTx.setTtShzt(bizTx.getTtShzt());//提现审核状态(0、待审核 1、审核通过 2、审核拒绝)
        newBizTx.setTtBz(bizTx.getTtBz());//审核描述

        int i = update(newBizTx);
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
        RuntimeCheck.ifBlank(bizTx.getTtZt(),"提现状态不能为空");//获取提现状态(0该红包待审核 1、红包已收取 2、红包已经发送  3、红包过期未收取到)

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



        // 更新佣金明细表
        bizYjmx.setZjZt("1");
        bizYjmx.setZjBz(bizTx.getTtBz());
        // 更新提现明细表
        update(bizTx);
        // 更新佣金明细表
        yjmxService.update(bizYjmx);

        return ApiResponse.success();
    }



    //==============================================================APP端  开始===========

    /**
     * 用户提现
     * @param ttje  金额
     * @param user
     * @return
     */
    public ApiResponse<String> saveUserDraw(Double ttje, String yhkh, String khh, String txXm,String ttFs, BizPtyh user){
        String userId=user.getId();//获取用户
        BizZh userZh=zhService.findById(userId);
        RuntimeCheck.ifFalse(userZh != null && userZh.getYhZhye() >= ttje,"提现金额不能大于余额");
        RuntimeCheck.ifBlank(yhkh, "银行卡号不能为空");
//        RuntimeCheck.ifBlank(khh, "开户行不能为空");

        String yjid=genId();
        BizTx newEntity=new BizTx();
        if(StringUtils.isEmpty(ttFs)){
            newEntity.setTtFs("2");
        }else {
            newEntity.setTtFs(ttFs);
        }
        newEntity.setId(genId());
        newEntity.setYhId(userId);
        newEntity.setYhMc(user.getYhXm());
        newEntity.setTtJe(ttje);
        newEntity.setTtZt("0");
        newEntity.setTtSj(DateUtils.getNowTime());
        newEntity.setTtShzt("0");
        newEntity.setYjId(yjid);//佣金明细表id
        newEntity.setTtYhkh(yhkh);
        newEntity.setTtKhh(khh);
        newEntity.setTxXm(txXm);

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
           yjmxService.save(newBizYjmx);
       }
        // 更新账户表
        List<String> userList=new ArrayList<String>();

        if(org.apache.commons.lang.StringUtils.isNotEmpty(userId)){
            userList.add(userId);
        }
        zhService.userAccountUpdate(userList);
        return ApiResponse.success();
    }



}
