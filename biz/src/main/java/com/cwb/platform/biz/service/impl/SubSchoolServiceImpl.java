package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizSubSchoolMapper;
import com.cwb.platform.biz.model.BizSubSchool;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.SubSchoolService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.cwb.platform.util.exception.RuntimeCheckException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public class SubSchoolServiceImpl extends BaseServiceImpl<BizSubSchool,String> implements SubSchoolService {

    @Autowired
    private BizSubSchoolMapper baseMapper;
    @Autowired
    private PtyhService service;

    @Override
    protected Mapper<BizSubSchool> getBaseMapper() {
        return baseMapper;
    }


    @Override
    public ApiResponse<String> validAndSave(BizSubSchool entity){
        RuntimeCheck.ifBlank(entity.getSubCode(), "代培点代码不能为空");
        RuntimeCheck.ifBlank(entity.getSubName(), "代培点名称不能为空");
        RuntimeCheck.ifBlank(entity.getSubPhone(), "负责人手机号码不能为空");
        List<BizPtyh> ptyhs = service.findEq(BizPtyh.InnerColumn.yhZh, entity.getSubPhone());
        RuntimeCheck.ifEmpty(ptyhs, "此号码未在平台注册");
        BizPtyh ptyh = ptyhs.get(0);
        entity.setSubFz(ptyh.getYhXm());
        entity.setSubOpenid(ptyh.getYhOpenId());
        entity.setYhId(ptyh.getId());
        entity.setCjsj(DateUtils.getNowTime());
        entity.setId(genId());
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        String string = getRequestParameterAsString("condition");
        if(StringUtils.isNotBlank(string)){
            condition.and().andCondition(" sub_code like '%"+string+"%' or sub_name like '%"+string+"%' or sub_phone like '%"+string+"%' or sub_fz like '%"+string+"%'");
        }
        return true;
    }






}
