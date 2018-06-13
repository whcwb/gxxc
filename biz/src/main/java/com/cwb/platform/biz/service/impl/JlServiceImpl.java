package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizJlMapper;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.mapper.BizUserMapper;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.JlService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class JlServiceImpl extends BaseServiceImpl<BizJl,String> implements JlService {

    @Autowired
    private BizJlMapper entityMapper;
    @Autowired
    private BizPtyhMapper bizPtyhMapper;
    @Autowired
    private BizUserMapper userMapper;

    @Autowired
    private PtyhService ptyhService;
    @Override
    protected Mapper<BizJl> getBaseMapper() {
        return entityMapper;
    }
    /**
     * 更新教练认证状态
     *
     * yhJlsh 1成功  2 失败
     *
     * @return
     */
    @Override
    public ApiResponse<String> updateYhRz(BizPtyh obd){
        SysYh sysYh = getCurrentUser();
        BizPtyh user = bizPtyhMapper.selectByPrimaryKey(obd.getId());
        if (user == null) return ApiResponse.fail("用户不存在");

        RuntimeCheck.ifTrue(!StringUtils.equals(user.getYhJlsh(), "0"), "操作失败，只有提交教练资料才能进行认证操作");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhJlsh(), "1"), "操作失败，该教练已认证无需再次认证");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhSfsd(), "1"), "操作失败，该教练已锁定无法进行认证操作");
        RuntimeCheck.ifFalse(StringUtils.equals(user.getYhSfyjz(), "1"), "操作失败，该教练无驾照无法进行认证操作");

        RuntimeCheck.ifBlank(obd.getYhJlsh(), "审核状态不能为空");
        if (StringUtils.containsNone(obd.getYhJlsh(), new char[]{'1', '2'})) {
            return ApiResponse.fail("请输入正确审核状态");
        }

        BizJl jlInfo=this.findById(user.getId());
        if (jlInfo == null) return ApiResponse.fail("教练信息不存在");


        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(user.getId());

        String yhZtMs="";
        yhZtMs=obd.getYhZtMs();
        if(StringUtils.equals("2",obd.getYhJlsh())){
            RuntimeCheck.ifBlank(yhZtMs, "请填写审核失败原因。");
        } else if(StringUtils.equals("1",obd.getYhJlsh())){
            if(!StringUtils.equals(user.getYhZtMs(),"1")){//如果学员资料没有审核，就需要将学员资料进行同步审核
                //biz_ptyh平台用户表 字段拼装
                String yhXm=jlInfo.getYhXm();
                String yhZjhm=jlInfo.getYhZjhm();

                String sex;//获取性别 ZDCLK0042(1、男;2、女)
                if (Integer.parseInt(yhZjhm.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
                    sex = "2";
                } else {
                    sex = "1";
                }

                newEntity.setYhXm(yhXm);//用户姓名
                newEntity.setYhZjhm(yhZjhm);//用户证件号码
                newEntity.setYhXb(sex);//用户性别
                newEntity.setYhSfyjz("1");//用户驾照状态不能为空


                //更新用户实名表
                //      获取用户父级ID
                String yhSjid = "";//设置上级ID
                String yhSsjid = "";//上上级ID

                String yhYyyqm = user.getYhYyyqm();//该用户的父级ID
                SimpleCondition newCondition = new SimpleCondition(BizPtyh.class);
                newCondition.eq(BizPtyh.InnerColumn.yhZsyqm.name(), yhYyyqm);
                List<BizPtyh> bizPtyhsList = ptyhService.findByCondition(newCondition);
                if (bizPtyhsList == null) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
                if (bizPtyhsList.size() != 1) return ApiResponse.fail("用户资料存在异常，请联系管理处理!");
                String pUserId = bizPtyhsList.get(0).getId();//获取出父级ID
                yhSjid = pUserId;
                BizUser pBizUser = userMapper.selectByPrimaryKey(yhSjid);//获取出上上级ID
                if (pBizUser != null) {
                    yhSsjid = pBizUser.getYhSjid();
                }

                //修改用户实名表  biz_user
                BizUser bizUser = new BizUser();
                bizUser.setYhId(user.getId());//用户ID
                bizUser.setYhZjhm(jlInfo.getYhZjhm());//用户证件号码
                bizUser.setYhSjhm(user.getYhZh());//用户账户
                bizUser.setYhSfjsz(user.getYhSfyjz());//设置是否有驾驶证(1:有 2:没有)
                bizUser.setYhXm(jlInfo.getYhXm());//姓名
                bizUser.setCjsj(DateUtils.getNowTime());//创建时间
                bizUser.setYhSjid(yhSjid);//设置上级ID
                bizUser.setYhSsjid(yhSsjid);//上上级ID

                userMapper.deleteByPrimaryKey(user.getId());
                userMapper.insert(bizUser);

            }
            newEntity.setYhZt(obd.getYhJlsh());
            newEntity.setYhLx("2");//设置类型 ZDCLK0041(2、教练、1、学员)
        }
        newEntity.setYhJlsh(obd.getYhJlsh());
        newEntity.setYhJlMs(yhZtMs);
       int i = ptyhService.update(newEntity);
       if(i>0){
           BizJl jl=new BizJl();
           jl.setYhId(newEntity.getId());
           jl.setJlShZt(obd.getYhJlsh());
           jl.setJlShMs(yhZtMs);
           this.update(jl);
       }
        return i==1?ApiResponse.success():ApiResponse.fail("审核失败");
    }
}
