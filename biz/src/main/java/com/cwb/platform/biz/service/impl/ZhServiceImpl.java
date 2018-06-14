package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizZhMapper;
import com.cwb.platform.biz.model.BizZh;
import com.cwb.platform.biz.service.ZhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class ZhServiceImpl extends BaseServiceImpl<BizZh,java.lang.String> implements ZhService{
    @Autowired
    private BizZhMapper entityMapper;

    @Override
    protected Mapper<BizZh> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizZh.class;
    }

    /**
     * 更新用户账户信息
     * 1、删除该账户信息
     * 2、从拥金明细表中统计出所有的金额
     *
     * 统计明细表中  提现状态 ZDCLK0054 (0、提现冻结  1、 处理成功 2、提现失败) 提现操作默认0 佣金操作默认1
     * @param userId
     * @return
     */
    public boolean userAccountUpdate(List<String> userId) {
        Boolean ret=false;
        if(userId!=null&& userId.size()>0){
            entityMapper.deleteUserDetail(userId);
            entityMapper.updatUserAccount(userId);
        }
        ret=true;
        return ret;
    }
   
}
