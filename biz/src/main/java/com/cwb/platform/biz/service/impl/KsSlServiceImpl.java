package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizKsSlMapper;
import com.cwb.platform.biz.model.BizKsSl;
import com.cwb.platform.biz.model.BizKsYk;
import com.cwb.platform.biz.service.KsSlService;
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
 * 学员考试受理信息表
 * Created by Administrator on 2018/6/19.
 */
@Service
public class KsSlServiceImpl extends BaseServiceImpl<BizKsSl,String> implements KsSlService {
    @Autowired
    private BizKsSlMapper entityMapper;

    @Autowired
    private PtyhService ptyhService;


    @Override
    protected Mapper<BizKsSl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizKsSl.class;
    }

    @Override
    public ApiResponse<String> validAndSave(BizKsSl entity) {
        int i=save(entity);
        return i==1?ApiResponse.success():ApiResponse.fail();
    }
    @Override
    public int save(BizKsSl entity) {
        RuntimeCheck.ifBlank(entity.getCode(), "请选择机构");
        RuntimeCheck.ifBlank(entity.getName(), "请确定机构名称");
        RuntimeCheck.ifBlank(entity.getName(), "请确定机构名称");

        RuntimeCheck.ifBlank(entity.getSlType(), "审核状态不能为空");
        if (org.apache.commons.lang.StringUtils.containsNone(entity.getSlType(), new char[]{'1', '2', '3', '4'})) {
            RuntimeCheck.ifTrue(true,"请输入正确审核状态");
        }

        SysYh user=getCurrentUser();
        entity.setId(genId());
        entity.setCjr(user.getYhid());//操作人ID
        entity.setCjsj(DateUtils.getNowTime());//创建时间
        BizPtyh ptyh=ptyhService.findByIdSelect(entity.getYhId());
        RuntimeCheck.ifTrue(ptyh == null, "用户资料有误！");
        entity.setYhZjhm(ptyh.getYhZjhm());//用户证件号码
        entity.setYhXm(ptyh.getYhXm());//用户姓名
        return entityMapper.insertSelective(entity);
    }

    @Override
    public ApiResponse<Map<String,BizKsSl>> getHandleStatus(String yhId) {
        RuntimeCheck.ifBlank(yhId,"请选择学员");
        SimpleCondition condition1 = new SimpleCondition(BizKsSl.class);
        condition1.setOrderByClause("SL_SJ desc");
        condition1.eq(BizKsSl.InnerColumn.yhId,yhId);
        condition1.eq(BizKsSl.InnerColumn.slType,"1");

        SimpleCondition condition2 = new SimpleCondition(BizKsSl.class);
        condition2.setOrderByClause("SL_SJ desc");
        condition2.eq(BizKsSl.InnerColumn.yhId,yhId);
        condition2.eq(BizKsSl.InnerColumn.slType,"2");

        SimpleCondition condition3 = new SimpleCondition(BizKsSl.class);
        condition3.setOrderByClause("SL_SJ desc");
        condition3.eq(BizKsSl.InnerColumn.yhId,yhId);
        condition3.eq(BizKsSl.InnerColumn.slType,"3");

        SimpleCondition condition4 = new SimpleCondition(BizKsSl.class);
        condition4.setOrderByClause("SL_SJ desc");
        condition4.eq(BizKsSl.InnerColumn.yhId,yhId);
        condition4.eq(BizKsSl.InnerColumn.slType,"4");

        List<BizKsSl> list1 = entityMapper.selectByExampleAndRowBounds(condition1,new RowBounds(0,1));
        List<BizKsSl> list2 = entityMapper.selectByExampleAndRowBounds(condition2,new RowBounds(0,1));
        List<BizKsSl> list3 = entityMapper.selectByExampleAndRowBounds(condition3,new RowBounds(0,1));
        List<BizKsSl> list4 = entityMapper.selectByExampleAndRowBounds(condition4,new RowBounds(0,1));

        Map<String,BizKsSl> map = new HashMap<>(4);
        if (list1.size() != 0){
            map.put("1",list1.get(0));
        }
        if (map.size() != 0){
            map.put("2",list2.get(0));
        }
        if (list3.size() != 0){
            map.put("3",list3.get(0));
        }
        if (list4.size() != 0){
            map.put("4",list4.get(0));
        }
        return ApiResponse.success(map);
    }
}
