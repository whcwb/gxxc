package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizKsJgMapper;
import com.cwb.platform.biz.model.BizKsJg;
import com.cwb.platform.biz.service.KsjgService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 考试结果记录表
 * Created by Administrator on 2018/6/19.
 */
@Service
public class KsjgServiceImpl extends BaseServiceImpl<BizKsJg,String> implements KsjgService{
    @Autowired
    private BizKsJgMapper entityMapper;

    @Autowired
    private PtyhService ptyhService;

    @Override
    protected Mapper<BizKsJg> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizKsJg.class;
    }


    @Override
    public int save(BizKsJg entity) {
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
