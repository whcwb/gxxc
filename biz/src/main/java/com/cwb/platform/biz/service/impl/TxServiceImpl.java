package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.YjmxService;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizTxMapper;
import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.biz.service.TxService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

@Service
public class TxServiceImpl extends BaseServiceImpl<BizTx,java.lang.String> implements TxService{
    @Autowired
    private BizTxMapper entityMapper;

    @Autowired
    private YjmxService yjmxService;

    @Override
    protected Mapper<BizTx> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizTx.class;
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
        BizTx tx = findById(bizTx.getId());
        RuntimeCheck.ifTrue(ObjectUtils.isEmpty(tx),"根据主键id无法查询到该提现明细");
        RuntimeCheck.ifTrue(StringUtils.equals(tx.getTtShzt(),"1"),"该记录已经审核通过");
        RuntimeCheck.ifTrue(StringUtils.equals(tx.getTtShzt(),"2"),"该记录已经被驳回");
        RuntimeCheck.ifTrue(StringUtils.containsNone(tx.getTtFs(),"2"),"提现方式为人工转账才能审核");

        int i = update(bizTx);

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
        RuntimeCheck.ifBlank(bizTx.getTtZt(),"提现状态不能为空");
        RuntimeCheck.ifBlank(bizTx.getTtBz(),"备注不能为空");
        // 提现明细
        BizTx tx = findById(bizTx.getId());
        RuntimeCheck.ifTrue(ObjectUtils.isEmpty(tx),"根据主键id无法查询到该提现明细");
        RuntimeCheck.ifTrue(StringUtils.equals(tx.getTtZt(),bizTx.getTtZt()),"修改提现状态不能与当前状态相同");
        RuntimeCheck.ifTrue(StringUtils.containsNone(tx.getTtShzt(),"1"),"该记录审核未通过或被驳回");
        // 佣金明细
        BizYjmx bizYjmx = yjmxService.findById(tx.getYjId());
        RuntimeCheck.ifTrue(ObjectUtils.isEmpty(bizYjmx),"根据佣金明细id无法查询到佣金明细");
        RuntimeCheck.ifTrue(StringUtils.equals(bizYjmx.getZjFs(),"-1"),"必须是提现才能修改提现状态");



        // 更新佣金明细表
        bizYjmx.setZjZt("1");
        bizYjmx.setZjBz(bizTx.getTtBz());
        // 更新提现明细表
        update(bizTx);
        // 更新佣金明细表
        yjmxService.update(bizYjmx);

        return ApiResponse.success();
    }



}
