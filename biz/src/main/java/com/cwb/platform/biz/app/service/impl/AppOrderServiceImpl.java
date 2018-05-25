package com.cwb.platform.biz.app.service.impl;


import com.cwb.platform.biz.app.service.AppOrderService;
import com.cwb.platform.biz.mapper.BizOrderMapper;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.impl.PtyhServiceImpl;
import com.cwb.platform.biz.service.impl.UserServiceImpl;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppOrderServiceImpl extends BaseServiceImpl<BizOrder,String> implements AppOrderService {
    @Autowired
    private BizOrderMapper entityMapper;

    @Autowired
    private BizPtyhMapper ptyhMapper;

    @Autowired
    private PtyhServiceImpl ptyhService;

    @Autowired
    private UserServiceImpl userService;

    @Override
    protected Mapper<BizOrder> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizOrder.class;
    }

    @Value("${order_money:2500}")
    private String orderMoney;
    /**
     * 分页补充   按全部、已付款、待付款 来进行查询
     * @param condition
     *
     * @return
     */
    public boolean fillPagerCondition(LimitedCondition condition){
        BizPtyh user=getAppCurrentUser();
        RuntimeCheck.ifNull(user,"用户不存在");
        String userId=user.getId();

//        condition.and().andEqualTo(BizOrder.InnerColumn.yhSjid.name(),userId);
//        condition.or().andEqualTo(BizOrder.InnerColumn.yhSsjid.name(),userId);
        condition.and().andCondition(" ( YH_SJID='"+userId+"' OR YH_SSJID='"+userId+"') ");
        return true;
    }

    @Override
    protected void afterPager(PageInfo<BizOrder> resultPage){
        BizPtyh user=getAppCurrentUser();
        RuntimeCheck.ifNull(user,"用户不存在");
        String userId=user.getId();

        List<BizOrder> list = resultPage.getList();
        if(CollectionUtils.isNotEmpty(list)){
            List<String > ids = list.stream().map(BizOrder::getYhId).collect(Collectors.toList());

            SimpleCondition condition = new SimpleCondition(BizPtyh.class);
            condition.in(BizPtyh.InnerColumn.id.name(),ids);
            List<BizPtyh> userwjs = ptyhMapper.selectByExample(condition);

            Map<String,BizPtyh> userMap = userwjs.stream().collect(Collectors.toMap(BizPtyh::getId,p->p));

            for(BizOrder order:list){
                String orderUserId=order.getYhId();
                if (!userMap.containsKey(orderUserId))continue;
                BizPtyh zdlm = userMap.get(orderUserId);
                order.setUserDetail(ptyhService.afterReturns(zdlm));

                if(StringUtils.equals(userId,order.getYhSjid())){
                    order.setUserGrade("1");
                }else{
                    order.setUserGrade("2");
                }

            }
        }
        return;
    }
    public ApiResponse<Map<String,String>> saveAddOrder(BizOrder entity){
        Map<String,String> ret=new HashMap<String,String>();
        //获取支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
        RuntimeCheck.ifNull(entity.getDdZftd(),"您好，请确定支付方式");
        if(StringUtils.containsNone(entity.getDdZftd(), new char[]{'1', '2','3','4'})){
            RuntimeCheck.ifTrue(true,"您好，请输入确定支付方式");
        }
        BizPtyh user=getAppCurrentUser();
        RuntimeCheck.ifNull(user,"用户不存在");
        String userId=user.getId();
        BizPtyh userSelect = ptyhMapper.selectByPrimaryKey(userId);
        RuntimeCheck.ifNull(userSelect,"用户不存在");
        RuntimeCheck.ifTrue(StringUtils.equals(userSelect.getYhZt(),"0"),"您好，请您上传证件或等待管理员对您资料进行认证！");//认证状态 ZDCLK0043(0 未认证、1 已认证)
        RuntimeCheck.ifTrue(StringUtils.equals(userSelect.getDdSfjx(),"1"),"您已支付成功，无需再次支付！");//获取是否缴费(0无 1已缴费)
        RuntimeCheck.ifTrue(StringUtils.equals(userSelect.getYhSfsd(),"1"),"您已经锁定，无法支付。请联系管理人员进行解锁！");//用户是否锁定 ZDCLK0046 (0 否  1 是)  0是没有锁定 1是已锁定

        SimpleCondition condition = new SimpleCondition(BizOrder.class);
        condition.eq(BizOrder.InnerColumn.yhId.name(), userId);
        condition.eq(BizOrder.InnerColumn.ddZt.name(), "2");//订单状态(1、待缴费 2、已缴费 3、已退费)
        condition.eq(BizOrder.InnerColumn.ddZfzt.name(), "1");//支付状态（0,待支付 1、支付成功  2、支付失败）
        Integer count = this.countByCondition(condition);
        RuntimeCheck.ifTrue(count > 0,"您已支付成功，无需再次支付！");

        BizUser bizUser=userService.findById(userId);
        RuntimeCheck.ifNull(bizUser,"您好，请您上传证件资料进行认证！");

        BizOrder newEntity=new BizOrder();
        newEntity.setDdId(genId());
        newEntity.setYhId(user.getId());//用户id
        newEntity.setCjsj(DateUtils.getNowTime());//创建时间
        newEntity.setYhCjr(userId);//创建人
        newEntity.setDdZt("1");//订单状态(ZDCLK0037 1、待缴费 2、已缴费 3、已退费)
        newEntity.setDdZftd(entity.getDdZftd());//支付通道(1、支付宝  2、微信  3、银联  4、快钱……)
//            newEntity.setDdZfsj();//支付时间
        newEntity.setDdZfzt("0");//支付状态（0,待支付 1、支付成功  2、支付失败）
        newEntity.setDdZfje(new BigDecimal(orderMoney).doubleValue());//支付金额(单位 分)  todo 金额需要怎么确定，是否从配置文件中做处理
//            newEntity.setDdZfpz();//支付凭证ID(保存支付通道返回的CODE)
//            newEntity.setDdZfjg();//支付响应结果(1:成功 2:失败)
        newEntity.setYhXm(user.getYhXm());//姓名
//            newEntity.setDdBz();//订单备注
        newEntity.setYhSjid(bizUser.getYhSjid());//上级ID
        newEntity.setYhSsjid(bizUser.getYhSsjid());//上上级ID
        entityMapper.insert(newEntity);



        return ApiResponse.success(ret);

    }
}
