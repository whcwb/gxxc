package com.cwb.platform.biz.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizOrderMapper;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.service.OrderService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

@Service
public class OrderServiceImpl extends BaseServiceImpl<BizOrder,java.lang.String> implements OrderService{
    @Autowired
    private BizOrderMapper entityMapper;

    @Override
    protected Mapper<BizOrder> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizOrder.class;
    }

   
}
