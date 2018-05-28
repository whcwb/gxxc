package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizHdMapper;
import com.cwb.platform.biz.model.BizHd;
import com.cwb.platform.biz.service.HdService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;

@Service
public class HdServiceImpl extends BaseServiceImpl<BizHd,String> implements HdService{
    @Autowired
    private BizHdMapper entityMapper;

    @Override
    protected Mapper<BizHd> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizHd.class;
    }

    @Override
    public ApiResponse<String> saveEntity(BizHd entity) {
        SysYh sysYh = getCurrentUser();
        RuntimeCheck.ifBlank(entity.getHdBt(),"标题不能为空");
        RuntimeCheck.ifBlank(entity.getHdZw(),"正文不能为空");
        RuntimeCheck.ifBlank(entity.getHdSx(), "属性不能为空");
        if(StringUtils.containsNone(entity.getHdSx(), new char[]{'1', '2'})){
            return ApiResponse.fail("请输入正确的属性");
        }

        entity.setHdtj(null);
        entity.setId(genId());
        entity.setCjsj(DateUtils.getNowTime());
        entity.setHdCjr(sysYh.getYhid());
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public ApiResponse<String> updateEntity(BizHd entity) {
        SysYh sysYh = getCurrentUser();
        RuntimeCheck.ifBlank(entity.getId(), "主键id不能为空");
        RuntimeCheck.ifBlank(entity.getHdBt(),"标题不能为空");
        RuntimeCheck.ifBlank(entity.getHdZw(),"正文不能为空");
        RuntimeCheck.ifBlank(entity.getHdSx(), "属性不能为空");
        if(StringUtils.containsNone(entity.getHdSx(), new char[]{'1', '2'})){
            return ApiResponse.fail("请输入正确的属性");
        }

        entity.setHdtj(null);
        entity.setHdxgsj(DateUtils.getNowTime());
        entity.setHdxgr(sysYh.getYhid());
        update(entity);
        return ApiResponse.updateSuccess();
    }

    @Override
    public ApiResponse<String> activityRecommend(BizHd entity){
        RuntimeCheck.ifBlank(entity.getId(),"请选择推荐活动");
        BizHd newEntity=new BizHd();
        newEntity.setId(entity.getId());
        if(StringUtils.isBlank(entity.getHdtj())){
            newEntity.setHdtj("");
        }else{
            newEntity.setHdtj(DateUtils.getNowTime());
        }
        int i=update(newEntity);
        return i==1?ApiResponse.success():ApiResponse.fail();
    }

    /**
     * 分页补充
     * @param condition
     * @return
     */
    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()) .getRequest();
        String hdSx = request.getParameter("hdSxs");
        if(StringUtils.equals(hdSx,"0")){
            condition.and().andIsNotNull("hdtj");
            condition.and().andNotEqualTo("hdtj","");
            condition.setOrderByClause("HD_TJ desc");
        }else{
            condition.eq(BizHd.InnerColumn.hdSx,hdSx);
            condition.setOrderByClause("CJSJ desc");
        }
        return true;
    }
}
