package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.app.service.impl.AppOrderServiceImpl;
import com.cwb.platform.biz.mapper.BizOrderMapper;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.mapper.BizTkMapper;
import com.cwb.platform.biz.mapper.BizUserMapper;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.model.BizTk;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.TkService;
import com.cwb.platform.biz.service.UserService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TkServiceImpl extends BaseServiceImpl<BizTk,String> implements TkService {

    @Autowired
    private BizTkMapper entityMapper;
    @Override
    protected Mapper<BizTk> getBaseMapper() {
        return entityMapper;
    }
    @Autowired
    private BizPtyhMapper ptyhMapper;
    @Autowired
    private BizUserMapper userMapper;
    @Autowired
    private BizOrderMapper orderMapper;
    @Autowired
    private AppOrderServiceImpl appOrderService;

    @Autowired
    private PtyhService ptyhService;
    @Autowired
    private UserService userService;
    @Override
    protected void afterPager(PageInfo<BizTk> resultPage) {
        List<BizTk> list = resultPage.getList();
        if (list.size() == 0){
            return;
        }
        if (CollectionUtils.isNotEmpty(list)) {
            for(BizTk order:list){
                order.setYhZjhm(order.getYhZjhm().replaceAll("(\\d{3})\\d*(\\d{4})", "$1******$2"));
            }
        }
        return;
    }

    /**
     * 提交退款申请
     * @param bizTk
     * @param ptyh
     * @return
     */
    @Override
    public ApiResponse<String> saveAddTk(BizTk bizTk, BizPtyh ptyh){
        String tkMessage=bizTk.getTkMessage();
        RuntimeCheck.ifBlank(tkMessage, "用户描述不能为空");
        String userId=ptyh.getId();//用户ID

        SimpleCondition condition = new SimpleCondition(BizTk.class);
        condition.eq(BizTk.InnerColumn.yhId.name(), userId);
        List<String> tkTypeList=new ArrayList<>();
        tkTypeList.add("0");
        tkTypeList.add("3");
        condition.in(BizTk.InnerColumn.tkType.name(), tkTypeList);
        Integer count = this.countByCondition(condition);

        RuntimeCheck.ifTrue(count!=null&&count>0,"您已有一条退款申请，暂时不能提交新的退款申请。");

        BizPtyh userSelect = ptyhMapper.selectByPrimaryKey(userId);
        SimpleCondition orderCondition = new SimpleCondition(BizOrder.class);
        orderCondition.eq(BizOrder.InnerColumn.yhId.name(), userId);
        orderCondition.eq(BizOrder.InnerColumn.ddZt.name(), "2");//订单状态(1、待缴费 2、已缴费 3、已退费)
        orderCondition.eq(BizOrder.InnerColumn.ddZfzt.name(), "1");//支付状态（0,待支付 1、支付成功  2、支付失败）
        orderCondition.and().andCondition(" CP_ID IN (SELECT ID FROM biz_cp WHERE CP_TYPE='1') ");
        Integer userOrderCount = appOrderService.countByCondition(orderCondition);//用户缴费数量
        String ykzt=userSelect.getYhXyYkType();//学员约考状态
        if(userOrderCount>0&&(!StringUtils.equals(ykzt,"41"))){
            //新增
            bizTk.setPkid(genId());
            bizTk.setCjsj(DateUtils.getNowTime());
            bizTk.setYhId(userId);
            bizTk.setYhXm(ptyh.getYhXm());
            bizTk.setYhZjhm(ptyh.getYhZjhm());
            bizTk.setTkType("0");
            entityMapper.insertSelective(bizTk);
        }else{
            return ApiResponse.fail("您还没有订单，如有疑问请与客服联系。");
        }
        return ApiResponse.success("");
    }

    /**
     * 退款申请
     * @param entity
     *
     * @return
     */
    @Override
    public ApiResponse<String> updateTkType(BizTk entity){
        SysYh sysYh=getCurrentUser(true);
        RuntimeCheck.ifBlank(entity.getPkid(), "请选择一条退款申请");
        RuntimeCheck.ifBlank(entity.getTkType(), "请至少一个选择退款状态");
        if(org.apache.commons.lang.StringUtils.containsNone(entity.getTkType(), new char[]{'1', '2','3'})){
            RuntimeCheck.ifTrue(true,"您好，请输入正确的退款状态状态");
        }

        String id = entity.getPkid();
        BizTk findBy=this.findById(id);
        RuntimeCheck.ifTrue(findBy==null,"请填写正确的退款申请");
        BizTk newBizTk=new BizTk();
        newBizTk.setPkid(entity.getPkid());
        newBizTk.setTkType(entity.getTkType());
        newBizTk.setTkMessage(entity.getTkMessage());
        if(StringUtils.equals(entity.getTkType(),"1")){//退款成功，需要将用户的一些信息给重置

            BizPtyh userSelect = ptyhMapper.selectByPrimaryKey(findBy.getYhId());

            SimpleCondition orderCondition = new SimpleCondition(BizOrder.class);
            orderCondition.eq(BizOrder.InnerColumn.yhId.name(), findBy.getYhId());
            orderCondition.eq(BizOrder.InnerColumn.ddZt.name(), "2");//订单状态(1、待缴费 2、已缴费 3、已退费)
            orderCondition.eq(BizOrder.InnerColumn.ddZfzt.name(), "1");//支付状态（0,待支付 1、支付成功  2、支付失败）
            orderCondition.and().andCondition(" CP_ID IN (SELECT ID FROM biz_cp WHERE CP_TYPE='1') ");
            Integer userOrderCount = appOrderService.countByCondition(orderCondition);//用户缴费数量
            String ykzt=userSelect.getYhXyYkType();//学员约考状态
            if(userOrderCount>0&&(!StringUtils.equals(ykzt,"41"))){
                BizPtyh tkPtyhr=new BizPtyh();
                tkPtyhr.setId(userSelect.getId());
                BizUser tkUser=new BizUser();
                tkUser.setYhId(userSelect.getId());
                String yhYqmgqsj=userSelect.getYhYqmgqsj();//用户邀请码过期时间
                Calendar c = Calendar.getInstance();
                if(StringUtils.isNotEmpty(yhYqmgqsj)){
                    Date yhYqmgq=null;
                    try {
                        yhYqmgq= DateUtils.getDate(yhYqmgqsj,"yyyy-MM-dd HH:mm:ss");

                    } catch (Exception e) {}
                    c.setTime(yhYqmgq);
                    c.add(Calendar.YEAR, -100);
                    Date y = c.getTime();

                    if(y.after(new Date())){//相减一年，还没有过期，就要将用户从学员变更成会员
                        tkPtyhr.setYhLx("3");
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        yhYqmgqsj = format.format(y);
                    }else{//
                        tkPtyhr.setDdSfjx("0");
                        yhYqmgqsj=" ";
                    }
                    tkPtyhr.setYhIxySffp("0");//学员是否已分配 ZDCLK0046 (0 否  1 是)
                    tkPtyhr.setYhFpms(" ");//学员分配描述
                    tkPtyhr.setK3jfzt(0);//科目三缴费状态  (0 否  1 是)
                    tkPtyhr.setYhXySlType("0");//学员受理状态
                    tkPtyhr.setYhXyYkType("0");//学员约考状态
                    tkPtyhr.setYhXyFpzyType("0");//学员分配专员情况
                    tkPtyhr.setYhYqmgqsj(yhYqmgqsj);
                    ptyhService.update(tkPtyhr);
                    BizUser user=userService.findById(userSelect.getId());
                    if(user!=null){
                        user.setXyZt(null);
                        user.setYhJlid(null);
                        user.setYhJlid1(null);
                        user.setYhJlid2(null);
                        user.setYhJlid3(null);
                        user.setYhJlid4(null);
                        userMapper.updateByPrimaryKey(user);
                    }
                    orderMapper.updateUserRefund(userSelect.getId(),newBizTk.getPkid());
                }
            }
        }
        newBizTk.setJbr(sysYh.getYhid());
        newBizTk.setJbrXm(sysYh.getXm());
        this.update(newBizTk);
        return ApiResponse.success();
    }






}
