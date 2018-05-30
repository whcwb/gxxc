package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizJlMapper;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.service.JlService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
@Service
<<<<<<< HEAD
public class JlServiceImpl extends BaseServiceImpl<BizJl, String> implements JlService {
=======
public class JlServiceImpl extends BaseServiceImpl<BizJl,String> implements JlService {
>>>>>>> ae32173d4149b92b4d44899eb17c98a59e47afdd

    @Autowired
    private BizJlMapper entityMapper;

    @Override
    protected Mapper<BizJl> getBaseMapper() {
        return entityMapper;
    }


}
