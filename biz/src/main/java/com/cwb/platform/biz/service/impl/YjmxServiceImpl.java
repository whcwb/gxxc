package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizYjmxMapper;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.YjmxService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YjmxServiceImpl extends BaseServiceImpl<BizYjmx,java.lang.String> implements YjmxService{
    @Autowired
    private BizYjmxMapper entityMapper;
    @Autowired
    private PtyhService ptyhService;
    @Autowired
    private YjmxService service;

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
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String zjhm = request.getParameter("zjhm");

        List<BizPtyh> list= ptyhService.findEq("yhZjhm",zjhm);
        if (CollectionUtils.isEmpty(list)){
            return false;
        }

        List<String> userIds = list.stream().map(BizPtyh::getId).collect(Collectors.toList());

        condition.and().andIn("yhId",userIds);
        return true;
    }
   
}
