package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizUserMapper;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.UserService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<BizUser,java.lang.String> implements UserService{
    @Autowired
    private BizUserMapper entityMapper;

    @Override
    protected Mapper<BizUser> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizUser.class;
    }


    @Override
    public void updateJlId(List<String> list, String jlId) {

        entityMapper.updateJlid(list, jlId);

    }
}
