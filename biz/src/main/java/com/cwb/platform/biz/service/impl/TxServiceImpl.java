package com.cwb.platform.biz.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizTxMapper;
import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.biz.service.TxService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class TxServiceImpl extends BaseServiceImpl<BizTx,java.lang.String> implements TxService{
    @Autowired
    private BizTxMapper entityMapper;

    @Override
    protected Mapper<BizTx> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizTx.class;
    }

   
}
