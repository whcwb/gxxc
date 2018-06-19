package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizKsYkMapper;
import com.cwb.platform.biz.model.BizKsYk;
import com.cwb.platform.biz.service.KsYkService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 学员考试约考表
 * Created by Administrator on 2018/6/19.
 */
@Service
public class KsYkServiceImpl extends BaseServiceImpl<BizKsYk,String> implements KsYkService {
    @Autowired
    private BizKsYkMapper entityMapper;

    @Override
    protected Mapper<BizKsYk> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizKsYk.class;
    }
}
