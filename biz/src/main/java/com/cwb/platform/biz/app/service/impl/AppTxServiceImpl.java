package com.cwb.platform.biz.app.service.impl;


import com.cwb.platform.biz.app.service.AppTxService;
import com.cwb.platform.biz.mapper.BizTxMapper;
import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
public class AppTxServiceImpl extends BaseServiceImpl<BizTx,String> implements AppTxService {
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
    /**
     * 分页补充   按全部、已付款、待付款 来进行查询
     * @param condition
     *
     * @return
     */
    public boolean fillPagerCondition(LimitedCondition condition){
        BizPtyh user=getAppCurrentUser();
        RuntimeCheck.ifNull(user,"用户不存在");
        String userId=user.getId();

        condition.and().andEqualTo(BizTx.InnerColumn.yhId.name(),userId);

        condition.setOrderByClause("ID desc");
        return true;
    }


}
