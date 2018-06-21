package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizJlMapper;
import com.cwb.platform.biz.mapper.BizYhpfMapper;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.model.BizYhpf;
import com.cwb.platform.biz.service.JlService;
import com.cwb.platform.biz.service.UserService;
import com.cwb.platform.biz.service.YhpfService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Administrator on 2018/6/21.
 */
@Service
public class YhpfServiceImpl extends BaseServiceImpl<BizYhpf,String> implements YhpfService {
    @Autowired
    private BizYhpfMapper entityMapper;
    @Autowired
    private BizJlMapper jlMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private JlService jlService;
    @Override
    protected Mapper<BizYhpf> getBaseMapper() {
        return entityMapper;
    }


    /**
     * 分页补充
     * @param condition
     * @return
     */
    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        BizPtyh user=getAppCurrentUser();
        String userId=user.getId();
        condition.and().andEqualTo(BizYhpf.InnerColumn.jlId.name(),userId);//登录人的账户
        condition.and().andEqualTo(BizYhpf.InnerColumn.auditType.name(),"1");//登录人的账户
        condition.setOrderByClause(BizYhpf.InnerColumn.id.desc());
        return true;
    }

    @Override
    public ApiResponse<String> validAndSave(BizYhpf entity) {
        RuntimeCheck.ifFalse(entity.getYhFz()!=null, "评分的分值不能为空");
        RuntimeCheck.ifFalse(entity.getYhPl()!=null, "评论不能为空");
        RuntimeCheck.ifFalse(entity.getYhFz()>=0&& entity.getYhFz()<=5,"评分的分值因该在[0-5]之间");

        BizPtyh user=getAppCurrentUser();
        BizUser userMessage=userService.findById(user.getId());
        RuntimeCheck.ifFalse(userMessage!=null,"您好，您还不能对教练进行评分");
        String jlId=userMessage.getYhJlid();
        RuntimeCheck.ifTrue(StringUtils.isEmpty(jlId),"您好，您还未分配教练不能对教练进行评分");
        BizJl jlMessage=jlService.findById(jlId);
        RuntimeCheck.ifFalse(jlMessage!=null,"您好，您还未分配教练不能对教练进行评分");

//        1、检查是否已经评论过，如果已经评论过就不许再评论
        BizYhpf yhpf=new BizYhpf();
        yhpf.setYhId(user.getId());
        yhpf.setJlId(jlId);
        int selecCount=entityMapper.selectCount(yhpf);
        RuntimeCheck.ifTrue(selecCount>0,"您好，您已对教练："+jlMessage.getYhXm()+"做出过评价，感谢您的评价");
//        2、进行评论操作
        yhpf.setId(genId());
        yhpf.setYhFz(entity.getYhFz());
        yhpf.setYhPl(entity.getYhPl());
        yhpf.setYhBm(user.getYhBm());
        yhpf.setJlXm(jlMessage.getYhXm());
        entityMapper.insertSelective(yhpf);

//        3、更新教练表的平均分
        jlMapper.updateJlPf(yhpf.getJlId());
        return ApiResponse.success();
    }



}