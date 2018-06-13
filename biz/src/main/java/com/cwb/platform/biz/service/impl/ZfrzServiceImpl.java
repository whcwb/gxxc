package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizZfrzMapper;
import com.cwb.platform.biz.model.BizZfrz;
import com.cwb.platform.biz.service.ZfrzService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.util.commonUtil.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class ZfrzServiceImpl extends BaseServiceImpl<BizZfrz,String> implements ZfrzService {
    @Autowired
    private BizZfrzMapper entityMapper;

    @Override
    protected Mapper<BizZfrz> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizZfrz.class;
    }

    public void addObject(BizZfrz payLog){
        payLog.setId(genId());
        payLog.setCjsj(DateUtils.getNowTime());// 创建时间
        try {
            entityMapper.insert(payLog);
        }catch (Exception e){}
    }
}
