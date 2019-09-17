package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizSubSchoolMapper;
import com.cwb.platform.biz.model.BizSubSchool;
import com.cwb.platform.biz.model.BizTrainPlace;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.SubSchoolService;
import com.cwb.platform.biz.service.TrainPlaceService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.cwb.platform.util.exception.RuntimeCheckException;
import org.apache.catalina.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class SubSchoolServiceImpl extends BaseServiceImpl<BizSubSchool,String> implements SubSchoolService {

    @Autowired
    private BizSubSchoolMapper baseMapper;
    @Autowired
    private PtyhService service;
    @Autowired
    private TrainPlaceService placeService;

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

        List<BizSubSchool> schoolList = findEq(BizSubSchool.InnerColumn.subCode, entity.getSubCode());
        RuntimeCheck.ifTrue(CollectionUtils.isNotEmpty(schoolList), "代码已经绑定代培点");

        BizPtyh ptyh = ptyhs.get(0);
        entity.setSubFz(ptyh.getYhXm());
        entity.setSubOpenid(ptyh.getYhOpenId());
        entity.setYhId(ptyh.getId());
        entity.setCjsj(DateUtils.getNowTime());
        entity.setId(entity.getSubCode());
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        String cond = getRequestParameterAsString("cond");
        if(StringUtils.isNotBlank(cond)){
            condition.and().andCondition(" sub_code like '%"+cond+"%' or sub_name like '%"+cond+"%' or sub_phone like '%"+cond+"%' or sub_fz like '%"+cond+"%'");
        }
        return true;
    }


    @Override
    public ApiResponse<String> getOpenid(String phone) {

        RuntimeCheck.ifBlank(phone, "请传入手机号");

        List<BizPtyh> list = service.findEq(BizPtyh.InnerColumn.yhZh, phone);
        RuntimeCheck.ifEmpty(list, "未找到用户信息");
        BizPtyh ptyh = list.get(0);
        // 更新下当前用户代培点的 openid
        List<BizSubSchool> schools = findEq(BizSubSchool.InnerColumn.subPhone, phone);
        if(CollectionUtils.isNotEmpty(schools)){
            schools.forEach(bizSubSchool -> {
                if(StringUtils.isNotBlank(ptyh.getYhOpenId())){
                    bizSubSchool.setSubOpenid(ptyh.getYhOpenId());
                    update(bizSubSchool);
                }
            });
        }
        return ApiResponse.success(ptyh.getYhOpenId());
    }

    @Override
    public ApiResponse<List<BizTrainPlace>> getAllTrainPlace(String id) {
        RuntimeCheck.ifBlank(id , "请选择代培点");
        List<BizTrainPlace> places = placeService.findEq("SUB_CODE", id);

        return ApiResponse.success(places);
    }

    @Override
    public ApiResponse<String> updateEntity(BizSubSchool subSchool) {
        RuntimeCheck.ifBlank(subSchool.getId(), "请选择代培点");
        BizSubSchool school = findById(subSchool.getId());
        if(!StringUtils.equals(school.getSubPhone(), subSchool.getSubPhone())){
            List<BizPtyh> ptyhs = service.findEq(BizPtyh.InnerColumn.yhZh, subSchool.getSubPhone());
            BizPtyh ptyh = ptyhs.get(0);
            subSchool.setSubOpenid(ptyh.getYhOpenId());
            subSchool.setSubFz(ptyh.getYhXm());
            subSchool.setYhId(ptyh.getId());
        }
        update(subSchool);
        return ApiResponse.success();
    }
}
