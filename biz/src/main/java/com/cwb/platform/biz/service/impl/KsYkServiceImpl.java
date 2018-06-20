package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizExamPlaceMapper;
import com.cwb.platform.biz.mapper.BizKsYkMapper;
import com.cwb.platform.biz.model.*;
import com.cwb.platform.biz.service.KsYkService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学员考试约考表
 * Created by Administrator on 2018/6/19.
 */
@Service
public class KsYkServiceImpl extends BaseServiceImpl<BizKsYk,String> implements KsYkService {
    @Autowired
    private BizKsYkMapper entityMapper;

    @Autowired
    private PtyhService ptyhService;

    @Autowired
    private BizExamPlaceMapper examPlaceMapper;


    @Override
    protected Mapper<BizKsYk> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizKsYk.class;
    }

    @Override
    public ApiResponse<String> validAndSave(BizKsYk entity) {
        int i=save(entity);
        return i==1?ApiResponse.success():ApiResponse.fail();
    }
    @Override
    public int save(BizKsYk entity) {
        SysYh user=getCurrentUser();
        entity.setId(genId());
        entity.setCjr(user.getYhid());//操作人ID
        entity.setCjsj(DateUtils.getNowTime());//创建时间
        BizPtyh ptyh=ptyhService.findByIdSelect(entity.getYhId());
        RuntimeCheck.ifTrue(ptyh == null, "用户资料有误！");

        RuntimeCheck.ifBlank(entity.getExamPlaceId(),"请选择考场");
        BizExamPlace examPlace = examPlaceMapper.selectByPrimaryKey(entity.getExamPlaceId());
        RuntimeCheck.ifNull(examPlace,"未找到考场");
        entity.setSchoolName(examPlace.getName());
        entity.setExamPlaceLat(examPlace.getLat());
        entity.setExamPlaceLng(examPlace.getLng());
        entity.setYhZjhm(ptyh.getYhZjhm());//用户证件号码
        entity.setYhXm(ptyh.getYhXm());//用户姓名
        return entityMapper.insertSelective(entity);
    }

    @Override
    public ApiResponse<Map<String,BizKsYk>> getUserExamInfo(String yhId) {
        RuntimeCheck.ifBlank(yhId,"请选择用户");
        SimpleCondition condition1 = new SimpleCondition(BizKsYk.class);
        condition1.setOrderByClause("cjsj desc");
        condition1.eq(BizKsYk.InnerColumn.yhId,yhId);
        condition1.eq(BizKsYk.InnerColumn.kmCode,"1");

        SimpleCondition condition2 = new SimpleCondition(BizKsYk.class);
        condition2.setOrderByClause("cjsj desc");
        condition2.eq(BizKsYk.InnerColumn.yhId,yhId);
        condition2.eq(BizKsYk.InnerColumn.kmCode,"2");

        SimpleCondition condition3 = new SimpleCondition(BizKsYk.class);
        condition3.setOrderByClause("cjsj desc");
        condition3.eq(BizKsYk.InnerColumn.yhId,yhId);
        condition3.eq(BizKsYk.InnerColumn.kmCode,"3");

        SimpleCondition condition4 = new SimpleCondition(BizKsYk.class);
        condition4.setOrderByClause("cjsj desc");
        condition4.eq(BizKsYk.InnerColumn.yhId,yhId);
        condition4.eq(BizKsYk.InnerColumn.kmCode,"4");


        List<BizKsYk> list1 = entityMapper.selectByExampleAndRowBounds(condition1,new RowBounds(0,1));
        List<BizKsYk> list2 = entityMapper.selectByExampleAndRowBounds(condition2,new RowBounds(0,1));
        List<BizKsYk> list3 = entityMapper.selectByExampleAndRowBounds(condition3,new RowBounds(0,1));
        List<BizKsYk> list4 = entityMapper.selectByExampleAndRowBounds(condition4,new RowBounds(0,1));

        Map<String,BizKsYk> examMap = new HashMap<>(4);
        if (list1.size() != 0){
            examMap.put("1",list1.get(0));
        }
        if (list2.size() != 0){
            examMap.put("2",list2.get(0));
        }
        if (list3.size() != 0){
            examMap.put("3",list3.get(0));
        }
        if (list4.size() != 0){
            examMap.put("4",list4.get(0));
        }
        return ApiResponse.success(examMap);
    }
}
