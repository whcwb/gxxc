package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizKsSlMapper;
import com.cwb.platform.biz.model.BizKsSl;
import com.cwb.platform.biz.service.KsSlService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

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
}
