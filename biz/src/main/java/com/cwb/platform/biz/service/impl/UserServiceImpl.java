package com.cwb.platform.biz.service.impl;


import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.mapper.BizUserMapper;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.UserService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<BizUser,java.lang.String> implements UserService{
    @Autowired
    private BizUserMapper entityMapper;

    @Override
    protected Mapper<BizUser> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizUser.class;
    }
    @Autowired
    private BizPtyhMapper ptyhMapper;

    @Autowired
    private PtyhServiceImpl ptyhService;

    @Override
    public void updateJlId(List<String> list, String jlId) {

        entityMapper.updateJlid(list, jlId);

    }

    @Override
    public List<String> getYhIds(List<String> ids) {
        return entityMapper.getYhIds(ids);
    }

    /**
     * 展示学员列表
     * @param yhid
     * @param xyZt
     * @param page
     * @return
     */
    @Override
    public ApiResponse<PageInfo<BizUser>> getStudentList(String yhid, String xyZt, Page<BizUser> page) {

        SysYh sysYh = getCurrentUser();
        RuntimeCheck.ifNull(sysYh, "未登录用户");
        RuntimeCheck.ifBlank(yhid, "用户id不能为空");


        BizPtyh bizPtyh = ptyhService.findById(yhid);
        // 验证用户是否认证
        RuntimeCheck.ifTrue(!StringUtils.equals(bizPtyh.getYhZt(),"1"), "用户尚未认证");
        RuntimeCheck.ifTrue(!StringUtils.equals(bizPtyh.getYhLx(),"2"), "该用户不是教练");

        // 获取该教练下的所有学员
        SimpleCondition condition = new SimpleCondition(BizUser.class);
        condition.eq(BizUser.InnerColumn.yhJlid.name(), yhid);
        if(StringUtils.isNotBlank(xyZt)){
            condition.eq(BizUser.InnerColumn.xyZt.name(),xyZt);
        }
        condition.setOrderByClause(" YH_ID DESC ");
        PageInfo<BizUser> userPageInfo = findPage(page,condition);
        // 获取每个学员的详细信息
        userPageInfo.getList().stream().forEach(
                bizUser -> {
                    // 根据yhid查询详细信息
                    BizPtyh ptyh = ptyhService.findById(bizUser.getYhId());
                    bizUser.setBizPtyh(ptyhService.afterReturns(ptyh));
                }
        );

        return ApiResponse.success(userPageInfo);
    }


    /**
     * 分页补充   按全部、已付款、待付款 来进行查询
     * @param condition
     *
     * @return
     */
    public boolean fillPagerCondition(LimitedCondition condition){
        SysYh user=getCurrentUser();
        RuntimeCheck.ifNull(user,"用户不存在");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()) .getRequest();
        String userGrade = request.getParameter("userGrade");//用户等级  1、一级用户   2、二级用户。不写查全部
        String userId = request.getParameter("userId");
        RuntimeCheck.ifBlank(userId,"用户id不能为空");
        if(StringUtils.equals(userGrade,"1")){//一级用户
            condition.and().andEqualTo(BizUser.InnerColumn.yhSjid.name(),userId);//上级ID
        }else  if(StringUtils.equals(userGrade,"2")) {//二级用户
            condition.and().andEqualTo(BizUser.InnerColumn.yhSsjid.name(),userId);//上上级ID
        }else{
//            condition.and().andEqualTo(BizUser.InnerColumn.yhSjid.name(),userId);//上级ID
//            condition.or().andEqualTo(BizUser.InnerColumn.yhSsjid.name(),userId);//上上级ID
//             上面这样写有问题。SQL会是 (YH_SJID='1') or (YH_SSJID='')    而我要的是：(YH_SJID='1' or YH_SSJID='') 所以不能满足我的需求
            condition.and().andCondition(" ( YH_SJID='"+userId+"' OR YH_SSJID='"+userId+"') ");

        }
        return true;
    }
    @Override
    protected void afterPager(PageInfo<BizUser> resultPage){
        SysYh user=getCurrentUser();
        RuntimeCheck.ifNull(user,"用户不存在");
        //String userId=user.getId();

        List<BizUser> list = resultPage.getList();
        if(CollectionUtils.isNotEmpty(list)){
            List<String > ids = list.stream().map(BizUser::getYhId).collect(Collectors.toList());

            SimpleCondition condition = new SimpleCondition(BizPtyh.class);
            condition.in(BizPtyh.InnerColumn.id.name(),ids);
            List<BizPtyh> userwjs = ptyhMapper.selectByExample(condition);

            Map<String,BizPtyh> userMap = userwjs.stream().collect(Collectors.toMap(BizPtyh::getId, p->p));

            for(BizUser order:list){
                String orderUserId=order.getYhId();
                if (!userMap.containsKey(orderUserId))continue;
                BizPtyh zdlm = userMap.get(orderUserId);
                order.setUserDetail(ptyhService.afterReturns(zdlm));
                if(StringUtils.equals(zdlm.getId(),order.getYhSjid())){
                    order.setUserGrade("1");
                }else{
                    order.setUserGrade("2");
                }

            }
        }
        return;
    }
}
