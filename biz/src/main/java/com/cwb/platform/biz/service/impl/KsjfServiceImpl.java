package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizKsJfMapper;
import com.cwb.platform.biz.model.BizKsJf;
import com.cwb.platform.biz.model.BizKsYk;
import com.cwb.platform.biz.service.KsYkService;
import com.cwb.platform.biz.service.KsjfService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.util.AsyncEventBusUtil;
import com.cwb.platform.biz.util.SendWechatMsgEvent;
import com.cwb.platform.biz.wxpkg.service.WechatService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.sys.util.ContextUtil;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.ExcelUtil;
import com.cwb.platform.util.exception.RuntimeCheck;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.bouncycastle.util.StringList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 学员考试缴费记录表
 * Created by Administrator on 2018/6/19.
 */
@Slf4j
@Service
public class KsjfServiceImpl extends BaseServiceImpl<BizKsJf, String> implements KsjfService {
    @Autowired
    private BizKsJfMapper entityMapper;
    @Autowired
    private PtyhService ptyhService;
    @Autowired
    private KsYkService ykService;

    @Value("${wxMsgTemplate.pay}")
    private String examMsgId;
    @Value("${staticPath}")
    private String staticPath;
    @Value("${wxDomain}")
    private String wxDomain;
    @Autowired
    private WechatService wechatService;
   @Autowired
    private AsyncEventBusUtil asyncEventBusUtil;
    @Override
    protected Mapper<BizKsJf> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return BizKsJf.class;
    }

    @Override
    public ApiResponse<String> validAndSave(BizKsJf entity) {
        RuntimeCheck.ifBlank(entity.getYhId(), "用户id不能为空");
        RuntimeCheck.ifBlank(entity.getKmId(), "用户的缴费科目不能为空");
        // 根据 yhid 和 科目编码 判断该用户是否已经缴费
        SimpleCondition condition = new SimpleCondition(BizKsJf.class);
        condition.eq(BizKsJf.InnerColumn.yhId.name(),entity.getYhId());
        condition.eq(BizKsJf.InnerColumn.kmId.name(), entity.getKmId());
        List<BizKsJf> ksJfList = findByCondition(condition);

        if(CollectionUtils.size(ksJfList) >= 1 ){
            return ApiResponse.fail("用户已经缴过当前科目的费用");
        }
        int i = save(entity);
        return i == 1 ? ApiResponse.success() : ApiResponse.fail();
    }

    @Override
    public int save(BizKsJf entity) {
        SysYh user = getCurrentUser();
        entity.setId(genId());
        entity.setCjr(user.getYhid());//操作人ID
        entity.setCjsj(DateUtils.getNowTime());//创建时间
        BizPtyh ptyh = ptyhService.findById(entity.getYhId());
        RuntimeCheck.ifTrue(ptyh == null, "用户资料有误！");
        entity.setYhZjhm(ptyh.getYhZjhm());//用户证件号码
        entity.setYhXm(ptyh.getYhXm());//用户姓名
        sendMsg(entity,ptyh);
        return entityMapper.insertSelective(entity);
    }

    @Override
    public ApiResponse<Map<String, String>> getPayInfo(String yhId) {
        RuntimeCheck.ifBlank(yhId, "请选择用户");
        SimpleCondition condition1 = new SimpleCondition(BizKsJf.class);
        condition1.setOrderByClause("cjsj desc");
        condition1.eq(BizKsJf.InnerColumn.yhId, yhId);
        condition1.eq(BizKsJf.InnerColumn.kmId, "1");

        SimpleCondition condition2 = new SimpleCondition(BizKsJf.class);
        condition2.setOrderByClause("cjsj desc");
        condition2.eq(BizKsJf.InnerColumn.yhId, yhId);
        condition2.eq(BizKsJf.InnerColumn.kmId, "2");

        SimpleCondition condition3 = new SimpleCondition(BizKsJf.class);
        condition3.setOrderByClause("cjsj desc");
        condition3.eq(BizKsJf.InnerColumn.yhId, yhId);
        condition3.eq(BizKsJf.InnerColumn.kmId, "3");

        SimpleCondition condition4 = new SimpleCondition(BizKsJf.class);
        condition4.setOrderByClause("cjsj desc");
        condition4.eq(BizKsJf.InnerColumn.yhId, yhId);
        condition4.eq(BizKsJf.InnerColumn.kmId, "4");


        List<BizKsJf> list1 = entityMapper.selectByExampleAndRowBounds(condition1, new RowBounds(0, 1));
        List<BizKsJf> list2 = entityMapper.selectByExampleAndRowBounds(condition2, new RowBounds(0, 1));
        List<BizKsJf> list3 = entityMapper.selectByExampleAndRowBounds(condition3, new RowBounds(0, 1));
        List<BizKsJf> list4 = entityMapper.selectByExampleAndRowBounds(condition4, new RowBounds(0, 1));

        Map<String, BizKsJf> payMap = new HashMap<>(4);
        if (list1.size() != 0) {
            payMap.put("1", list1.get(0));
        }
        if (list2.size() != 0) {
            payMap.put("2", list2.get(0));
        }
        if (list3.size() != 0) {
            payMap.put("3", list3.get(0));
        }
        if (list4.size() != 0) {
            payMap.put("4", list4.get(0));
        }

        ApiResponse<Map<String, BizKsYk>> examListRes = ykService.getUserExamInfo(yhId);
        if (examListRes.getResult() == null) return ApiResponse.success(new HashMap<>());
        Map<String, BizKsYk> examMap = examListRes.getResult();
        Map<String, String> map = new HashMap<>(4);

        try {
            for (Map.Entry<String, BizKsYk> entry : examMap.entrySet()) {
                // 判断考试是否通过，如果考试通过，则已缴费；如果考试未通过，则判断缴费时间
                BizKsYk exam = entry.getValue();
                int stand = (exam.getKmCode().equals("2") || exam.getKmCode().equals("3")) ? 80 : 90;
                if (exam.getCj1() >= stand || exam.getCj2() >= stand) {
                    map.put(entry.getKey(), "已缴");
                } else {
                    BizKsJf payInfo = payMap.get(entry.getKey());
                    if (payInfo == null) {
                        map.put(entry.getKey(), "未缴");
                    }else{
                        Date yksj = DateUtils.getDate(exam.getYkSj(), "yyyy-MM-dd");
                        Date jfsj = DateUtils.getDate(exam.getYkSj(), "yyyy-MM-dd");
                        if (yksj.getTime() > jfsj.getTime()) {
                            map.put(entry.getKey(), "未缴");
                        } else {
                            map.put(entry.getKey(), "已缴");
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ApiResponse.success(map);
    }

    @Override
    public ApiResponse<List<BizPtyh>> waitPaymentList(Integer km) {
        // 受理成功，未交科目一，则为科目一待缴费
        // 科目一考试通过，未交科目二，则为科目二待缴费
        // 科目二考试通过，未交科目三，则为科目三待缴费
        ApiResponse<List<BizPtyh>> res = new ApiResponse<>();
        SimpleCondition condition = new SimpleCondition(BizPtyh.class);
        condition.eq(BizPtyh.InnerColumn.k3jfzt,0);
        condition.eq(BizPtyh.InnerColumn.yhXySlType,"4");
        List<BizPtyh> userList = ptyhService.findByCondition(condition);
        if (userList.size() == 0){
            res.setResult(new ArrayList<>());
            return res;
        }

        List<String> userIds = userList.stream().map(BizPtyh::getId).collect(Collectors.toList());
        condition = new SimpleCondition(BizKsJf.class);
        condition.eq(BizKsJf.InnerColumn.kmId,km.toString());
        condition.in(BizKsJf.InnerColumn.yhId,userIds);
        List<BizKsJf> payedList = entityMapper.selectByExample(condition);
        List<String> payedUserIds = payedList.stream().map(BizKsJf::getYhId).collect(Collectors.toList());
        userList.removeIf(p->payedUserIds.contains(p.getId()));
        res.setResult(userList);
        return res;
    }

    @Override
    public List<List<String>> export(Integer km) {
        if (km == null){
            km = 1;
        }
        ApiResponse<List<BizPtyh>> res = waitPaymentList(km);
        int je = 120;
        if (km == 2)je = 152;
        if (km == 3)je = 230;
        List<List<String>> data = new ArrayList<>(res.getResult().size());
        for (BizPtyh user : res.getResult()) {
            List<String> row = new ArrayList<>();
            row.add(user.getYhXm());
            row.add(user.getYhZjhm());
            row.add(km.toString());
            row.add("");
            row.add(""+je);
            row.add("");
            data.add(row);
        }
        return data;
    }

    @Override
    public ApiResponse<String> batchImport(String filePath) {
        List<List<String>> data = ExcelUtil.getData(staticPath+filePath);
        data = data.subList(1,data.size());
        String now = DateUtils.getNowTime();
        for (List<String> stringList : data) {
            if (!stringList.get(3).equals("是")) {
                continue;
            }
            String zjhm = stringList.get(1);
            BizKsJf jf = new BizKsJf();
            jf.setYhZjhm(zjhm);
            jf.setKmId(stringList.get(2));
            jf.setJfSj(now);
            jf.setJfJl(stringList.get(4));
            jf.setJfFs(stringList.get(5));

            List<BizPtyh> userList = ptyhService.findEq(BizPtyh.InnerColumn.yhZjhm,zjhm);
            if (userList.size() != 0){
                BizPtyh user = userList.get(0);
                jf.setYhId(user.getId());
                jf.setYhXm(user.getYhXm());
            }
            save(jf);
        }
        return ApiResponse.success();
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

    private String sendMsg(BizKsJf dqjf, BizPtyh ptyh) {
        List<WxMpTemplateData> data = new ArrayList<>();
        String time = "";
        try {
            Date date = DateUtils.getDate(dqjf.getJfSj(), "yyyy-MM-dd");
            time = DateUtils.getDateStr(date, "yyyy年MM月dd日");
        } catch (ParseException e) {
            log.error("受理时间转换异常", e);
        }

        String km = getKm(dqjf.getKmId());
        String first  = "您于" + time +  "，通过" +dqjf.getJfFs()+ "，缴纳了"+km+"的考试费用"+dqjf.getJfJl()+"元";

        // 您于xx时间，通过xx方式，缴纳了科目X的考试费用XX元！



        data.add(new WxMpTemplateData("first", first));
        data.add(new WxMpTemplateData("keyword1", dqjf.getYhXm()));
        data.add(new WxMpTemplateData("keyword2", dqjf.getJfJl()+"元","#ff0000"));
        data.add(new WxMpTemplateData("keyword3",time));
        data.add(new WxMpTemplateData("remark", "点击查看"));
        WxMpTemplateMessage msg = new WxMpTemplateMessage();
        msg.setToUser(ptyh.getYhOpenId());
        msg.setTemplateId(examMsgId);
        msg.setUrl(wxDomain);
        msg.setData(data);
        asyncEventBusUtil.post(new SendWechatMsgEvent(msg));
        try {
            String res = wechatService.sendTemplateMsg(msg);
            log.info("sendMsg result :", res);
            return res;
        } catch (WxErrorException e) {
            log.error("发送微信模板消息异常", e);
        }
        return "未知错误";
    }
}
