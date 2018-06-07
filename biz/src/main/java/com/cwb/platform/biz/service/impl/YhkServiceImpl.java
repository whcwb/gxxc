package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizYhkMapper;
import com.cwb.platform.biz.model.BizYhk;
import com.cwb.platform.biz.service.YhkService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class YhkServiceImpl extends BaseServiceImpl<BizYhk,String> implements YhkService {
    @Autowired
    private BizYhkMapper entityMapper;

    @Override
    protected Mapper<BizYhk> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizYhk.class;
    }

   public List<BizYhk> getList(BizYhk entity){
       SimpleCondition condition = new SimpleCondition(BizYhk.class);
       //  根据用户ID查询出自己的银行卡
       condition.like(BizYhk.InnerColumn.yhId.name(), entity.getYhId());
       condition.setOrderByClause( BizYhk.InnerColumn.yhkScsysj.desc());
       List<BizYhk> bizJls = this.findByCondition(condition);
        return bizJls;
   }

    public ApiResponse<String> addUserCreditCard(BizYhk entity){
       entity.setId(genId());
       return this.validAndSave(entity);
    }
}
