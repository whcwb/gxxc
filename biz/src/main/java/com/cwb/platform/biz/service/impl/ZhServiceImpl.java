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
     * @param userId
     * @return
     */
    public boolean userAccountUpdate(List<String> userId) {
        Boolean ret=false;

        entityMapper.deleteUserDetail(userId);
        entityMapper.updatUserAccount(userId);
        ret=true;
        return ret;
    }
   
}
