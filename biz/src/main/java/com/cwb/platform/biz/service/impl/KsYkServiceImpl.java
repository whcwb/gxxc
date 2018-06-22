package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizExamPlaceMapper;
import com.cwb.platform.biz.mapper.BizKsYkMapper;
import com.cwb.platform.biz.model.*;
import com.cwb.platform.biz.service.KsYkService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.wxpkg.service.WechatService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.sys.model.SysZdxm;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.text.ParseException;
import java.util.*;

/**
 * 学员考试约考表
 * Created by Administrator on 2018/6/19.
 */
@Slf4j
@Service
public class KsYkServiceImpl extends BaseServiceImpl<BizKsYk, String> implements KsYkService {
    @Autowired
    private BizKsYkMapper entityMapper;

    @Autowired
    private PtyhService ptyhService;

    @Autowired
    private BizExamPlaceMapper examPlaceMapper;
    @Value("${wxMsgTemplate.exam}")
    private String examMsgId;

    @Value("${wxDomain}")
    private String wxDomain;
    @Autowired
    private WechatService wechatService;


    @Override
    protected Mapper<BizKsYk> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return BizKsYk.class;
    }

    @Override
    public ApiResponse<String> validAndSave(BizKsYk entity) {
        int i = save(entity);
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    @Override
    public int save(BizKsYk entity) {
        SysYh user = getCurrentUser();
        entity.setId(genId());
        entity.setCjr(user.getYhid());//操作人ID
        entity.setCjsj(DateUtils.getNowTime());//创建时间
        BizPtyh ptyh = ptyhService.findById(entity.getYhId());
        RuntimeCheck.ifTrue(ptyh == null, "用户资料有误！");

        RuntimeCheck.ifBlank(entity.getExamPlaceId(), "请选择考场");
        BizExamPlace examPlace = examPlaceMapper.selectByPrimaryKey(entity.getExamPlaceId());
        RuntimeCheck.ifNull(examPlace, "未找到考场");
        entity.setSchoolName(examPlace.getName());
        entity.setExamPlaceLat(examPlace.getLat());
        entity.setExamPlaceLng(examPlace.getLng());
        entity.setYhZjhm(ptyh.getYhZjhm());//用户证件号码
        entity.setYhXm(ptyh.getYhXm());//用户姓名
        sendMsg(entity,ptyh);
        return entityMapper.insertSelective(entity);
    }


    private String getKm(String code) {
        switch (code) {
            case "1":
                return "科目一";
            case "2":
                return "科目二";
            case "3":
                return "科目三";
            case "4":
                return "科目四";
            default:
        }
        return "";
    }

    private String sendMsg(BizKsYk dqks, BizPtyh ptyh) {
        List<WxMpTemplateData> data = new ArrayList<>();
        String time = "";
        try {
            Date date = DateUtils.getDate(dqks.getYkSj(), "yyyy-MM-dd");
            time = DateUtils.getDateStr(date, "yyyy年MM月dd日");
        } catch (ParseException e) {
            log.error("受理时间转换异常", e);
        }

        String first = "" ;
        String km = getKm(dqks.getKmCode());
        switch (dqks.getKmCode()){
            case "1":
                if (dqks.getCj1()==null){
                    first +="您已经成功预约了"+time+km+"，"+dqks.getSchoolName()+"的考试，请按时前往！";
                }else{
                    if (dqks.getCj1()>=90||dqks.getCj2()>=90){
                        first +="恭喜您已经成功通过科目一的考试，现在您可以准备下一科目的学习了！";
                    }else {
                        first +="很遗憾，您的这次考试失败,请再接再厉，加油！";
                    }
                }
                break;
            case "2":
                if (dqks.getCj1()==null){
                    first +="您已经成功预约了"+time+km+"，"+dqks.getSchoolName()+"的考试，请按时前往！";
                }else{
                    if (dqks.getCj1()>=90||dqks.getCj2()>=90){
                        first +="恭喜您已经成功通过科目二的考试，现在您可以准备下一科目的学习了！";
                    }else {
                        first +="很遗憾，您的这次考试失败，请再接再厉，加油！";
                    }
                }
                break;
            case "3":
                if (dqks.getCj1()==null){
                    first +="您已经成功预约了"+time+km+"，"+dqks.getSchoolName()+"的考试，请按时前往！";
                }else{
                    if (dqks.getCj1()>=90||dqks.getCj2()>=90){
                        first +="恭喜您已经成功通过科目三的考试，现在您可以准备下一科目的学习了！";
                    }else {
                        first +="很遗憾，您的这次考试失败，请再接再厉，加油！";
                    }
                }
                break;
            case "4":
                if (dqks.getCj1()==null){
                    first +="您已经成功预约了"+time+km+"，"+dqks.getSchoolName()+"的考试，请按时前往！";
                }else{
                    if (dqks.getCj1()>=90||dqks.getCj2()>=90){
                        first +="恭喜您已经成功通过科目四的考试，走向你的老司机之路吧！";
                    }else {
                        first +="很遗憾，您的这次考试失败，请再接再厉，加油！";
                    }
                }
                break;
            default:

        }


        data.add(new WxMpTemplateData("first", first));
        data.add(new WxMpTemplateData("keyword1", time));
        data.add(new WxMpTemplateData("keyword2",dqks.getSchoolName()));
        data.add(new WxMpTemplateData("remark", "考试科目："+km,"#ff0000"));
        WxMpTemplateMessage msg = new WxMpTemplateMessage();
        msg.setToUser(ptyh.getYhOpenId());
        msg.setTemplateId(examMsgId);
        msg.setUrl(wxDomain);
        msg.setData(data);
        try {
            String res = wechatService.sendTemplateMsg(msg);
            log.info("sendMsg result :", res);
            return res;
        } catch (WxErrorException e) {
            log.error("发送微信模板消息异常", e);
        }
        return "未知错误";
    }


    @Override
    public ApiResponse<Map<String, BizKsYk>> getUserExamInfo(String yhId) {
        RuntimeCheck.ifBlank(yhId, "请选择用户");
        SimpleCondition condition1 = new SimpleCondition(BizKsYk.class);
        condition1.setOrderByClause("cjsj desc");
        condition1.eq(BizKsYk.InnerColumn.yhId, yhId);
        condition1.eq(BizKsYk.InnerColumn.kmCode, "1");

        SimpleCondition condition2 = new SimpleCondition(BizKsYk.class);
        condition2.setOrderByClause("cjsj desc");
        condition2.eq(BizKsYk.InnerColumn.yhId, yhId);
        condition2.eq(BizKsYk.InnerColumn.kmCode, "2");

        SimpleCondition condition3 = new SimpleCondition(BizKsYk.class);
        condition3.setOrderByClause("cjsj desc");
        condition3.eq(BizKsYk.InnerColumn.yhId, yhId);
        condition3.eq(BizKsYk.InnerColumn.kmCode, "3");

        SimpleCondition condition4 = new SimpleCondition(BizKsYk.class);
        condition4.setOrderByClause("cjsj desc");
        condition4.eq(BizKsYk.InnerColumn.yhId, yhId);
        condition4.eq(BizKsYk.InnerColumn.kmCode, "4");


        List<BizKsYk> list1 = entityMapper.selectByExampleAndRowBounds(condition1, new RowBounds(0, 1));
        List<BizKsYk> list2 = entityMapper.selectByExampleAndRowBounds(condition2, new RowBounds(0, 1));
        List<BizKsYk> list3 = entityMapper.selectByExampleAndRowBounds(condition3, new RowBounds(0, 1));
        List<BizKsYk> list4 = entityMapper.selectByExampleAndRowBounds(condition4, new RowBounds(0, 1));

        Map<String, BizKsYk> examMap = new HashMap<>(4);
        if (list1.size() != 0) {
            examMap.put("1", list1.get(0));
        }
        if (list2.size() != 0) {
            examMap.put("2", list2.get(0));
        }
        if (list3.size() != 0) {
            examMap.put("3", list3.get(0));
        }
        if (list4.size() != 0) {
            examMap.put("4", list4.get(0));
        }
        return ApiResponse.success(examMap);
    }
}
