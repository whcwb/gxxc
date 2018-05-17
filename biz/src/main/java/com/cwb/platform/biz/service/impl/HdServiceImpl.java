package com.cwb.platform.biz.service.impl;


import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizHdMapper;
import com.cwb.platform.biz.model.BizHd;
import com.cwb.platform.biz.service.HdService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

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

        entity.setId(genId());
        entity.setCjsj(DateUtils.getNowTime());
        entity.setHdCjr(sysYh.getCjr());
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public ApiResponse<String> updateEntity(BizHd entity) {
        SysYh sysYh = getCurrentUser();
        RuntimeCheck.ifBlank(entity.getHdBt(),"标题不能为空");
        RuntimeCheck.ifBlank(entity.getHdZw(),"正文不能为空");
        RuntimeCheck.ifBlank(entity.getHdSx(), "属性不能为空");
        if(StringUtils.isNotBlank(entity.getHdtj())){
            entity.setHdtj(DateUtils.getNowTime());
        }
        entity.setHdxgsj(DateUtils.getNowTime());
        entity.setHdxgr(sysYh.getXgr());
        update(entity);
        return ApiResponse.updateSuccess();
    }
}
