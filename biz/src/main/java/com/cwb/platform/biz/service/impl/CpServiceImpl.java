package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizCpMapper;
import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.biz.service.CpService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;

@Service
public class CpServiceImpl extends BaseServiceImpl<BizCp,String> implements CpService {
    @Value("${order_money}")
    private String orderMoney;

    @Autowired
    private BizCpMapper entityMapper;

    @Override
    protected Mapper<BizCp> getBaseMapper() {
        return entityMapper;
    }

    /**
     * 查询一个类型的产品
     * @param cpType    产品类型（1、学费  2、补考费）必填
     * @return
     */
    public ApiResponse<BizCp> getCpTyetList(String cpType){

        BizCp bizCp=entityMapper.getCpTyetList(cpType);
        return ApiResponse.success(bizCp);
    }

    /**
     * 后台-新增产品
     * @param entity
     * @return
     */
    public ApiResponse<String> saveCp(BizCp entity){
//        1、参数验证
        SysYh sysYh = getCurrentUser();
        RuntimeCheck.ifBlank(entity.getCpMc(),"费用名称不能为空");
        RuntimeCheck.ifBlank(entity.getCpType(),"收费类型不能为空");
        RuntimeCheck.ifBlank(entity.getCpYj(),"请确定该产品分佣状态");

        Double cpJl=new BigDecimal(entity.getCpJl()).doubleValue();//产品金额
        RuntimeCheck.ifFalse(cpJl>0,"收费金额不能小于0");
        RuntimeCheck.ifFalse(cpJl>new BigDecimal(orderMoney).doubleValue(),"收费金额不能低于系统配置的最低价："+orderMoney);
        Double cpYjyjs=0.00;
        Double cpRjyjs=0.00;
        if(StringUtils.equals(entity.getCpYj(),"1")){
            Double cpYjyj=new BigDecimal(entity.getCpYjyj()).doubleValue();//设置一级佣金
            Double cpRjyj=new BigDecimal(entity.getCpRjyj()).doubleValue();//设置二级佣金
            RuntimeCheck.ifTrue(cpYjyj+cpRjyj>cpJl,"分佣金额不能大于总金额");//
            cpYjyjs=cpYjyj;
            cpRjyjs=cpRjyj;
        }
//2、将该类型所有产品设置为无效
        entityMapper.updateTypeDelete(entity.getCpType());//更新类型为无效
//        3、将新记录插入表中
        BizCp newBizCp=new BizCp();
        newBizCp.setId(genId());
        newBizCp.setCpMc(entity.getCpMc());//设置产品名称
        newBizCp.setCpType(entity.getCpType());//设置产品类型（1、学费  2、补考费）必填
        newBizCp.setCpJl(cpJl.toString());//设置产品总金额
        newBizCp.setCpYj(entity.getCpYj());//设置是否分佣(0不分 1分佣)
        newBizCp.setCpYjyj(cpYjyjs);//设置一级佣金
        newBizCp.setCpRjyj(cpRjyjs);//设置二级佣金
        newBizCp.setCpYx("1");//设置产品是否有效(0、无效 1、生效)
        newBizCp.setCjsj(DateUtils.getNowTime());//设置创建时间
        newBizCp.setCjr(sysYh.getYhid());//设置创建人

        entityMapper.insert(newBizCp);

        return ApiResponse.success();
    }

}
