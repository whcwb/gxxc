package com.cwb.platform.biz.app.service.impl;


import com.cwb.platform.biz.app.service.AppYjmxService;
import com.cwb.platform.biz.mapper.BizYjmxMapper;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class AppYjmxServiceImpl extends BaseServiceImpl<BizYjmx,String> implements AppYjmxService {
    @Autowired
    private BizYjmxMapper entityMapper;


    @Override
    protected Mapper<BizYjmx> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizYjmx.class;
    }

    /**
     * 分页补充
     * @param condition
     * @return
     */
    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        BizPtyh user=getAppCurrentUser();
        RuntimeCheck.ifNull(user,"用户不存在");
        String userId=user.getId();
        condition.and().andEqualTo(BizYjmx.InnerColumn.yhId.name(),userId);//登录人的账户
        return true;
    }
   
}
