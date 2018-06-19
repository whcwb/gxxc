package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizKsYkMapper;
import com.cwb.platform.biz.model.BizKsYk;
import com.cwb.platform.biz.service.KsYkService;
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
 * 学员考试约考表
 * Created by Administrator on 2018/6/19.
 */
@Service
public class KsYkServiceImpl extends BaseServiceImpl<BizKsYk,String> implements KsYkService {
    @Autowired
    private BizKsYkMapper entityMapper;

    @Autowired
    private PtyhService ptyhService;


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
        entity.setYhZjhm(ptyh.getYhZjhm());//用户证件号码
        entity.setYhXm(ptyh.getYhXm());//用户姓名
        return entityMapper.insertSelective(entity);
    }
}
