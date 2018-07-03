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
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.ExcelUtil;
import com.cwb.platform.util.exception.RuntimeCheck;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
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
    public ApiResponse<List<String>> batchImport(String filePath) {
        List<List<String>> data = ExcelUtil.getData(staticPath+filePath);
        RuntimeCheck.ifNull(data,"读取文件异常");
        data = data.subList(1,data.size());
        ApiResponse<List<String>> res = validImportData(data);
        if (!res.isSuccess()){
            return res;
        }
        String now = DateUtils.getNowTime();
        for (List<String> stringList : data) {
            if (!stringList.get(3).equals("是")) {
                continue;
            }
            RowData row = new RowData(stringList);
            String zjhm = row.getIdCard();
            BizKsJf jf = new BizKsJf();
            jf.setYhZjhm(zjhm);
            jf.setKmId(row.getSubject().toString());
            jf.setJfSj(now);
            jf.setJfJl(row.getMoney());
            jf.setJfFs(row.getMethod());

            List<BizPtyh> userList = ptyhService.findEq(BizPtyh.InnerColumn.yhZjhm,zjhm);
            if (userList.size() != 0){
                BizPtyh user = userList.get(0);
                jf.setYhId(user.getId());
                jf.setYhXm(user.getYhXm());
            }
            save(jf);
        }
        return res;
    }

    private ApiResponse<List<String>> validImportData(List<List<String>> data){
        // 必填字段是否都有
        // 学员身份证号不能重复
        // 学员身份证号是否存在
        // 同一个学员，同一个科目，只能交一次
        ApiResponse<List<String>> res = new ApiResponse<>();
        if (data.size() == 0){
            res.setCode(100);
            return res;
        }
        int c = 0;
        List<String> idCardList = new ArrayList<>(data.size());
        List<RowData> rowDataList = new ArrayList<>();
        Map<Integer,String> errorMap = new HashMap<>();
        Map<String,Integer> idCardRowNumMap = new HashMap<>();
        for (List<String> datum : data) {
            c ++;
            RowData row = new RowData(datum);
            rowDataList.add(row);
            if (!row.isPayed())continue;
            String error = "";
            if (StringUtils.isEmpty(row.getIdCard())){
                error += "身份证号码不能为空,";
            }else{
                if (idCardList.contains(row.getIdCard())){
                    error += "身份证号码重复";
                }else{
                    idCardRowNumMap.put(row.getIdCard(),c);
                    idCardList.add(row.getIdCard());
                }
            }
            if (row.getSubject() == null){
                error += "科目编码不能为空,";
            }
            if (StringUtils.isEmpty(row.getMoney())){
                error += "缴费金额不能为空,";
            }
            if (StringUtils.isNotEmpty(error)){
                errorMap.put(c,error);
            }
        }
        if (idCardList.size() == 0){
            if (errorMap.size() != 0){
                List<String> errors = new ArrayList<>();
                for (Map.Entry<Integer, String> entry : errorMap.entrySet()) {
                    errors.add("第"+entry.getKey()+"行："+entry.getValue());
                }
                res.setResult(errors);
                res.setCode(100);
            }
            res.setCode(100);
            return res;
        }
        Map<String,String> idCardYhIdMap = new HashMap<>();

        // 学员身份证号是否存在
        List<BizPtyh> userList = ptyhService.findIn(BizPtyh.InnerColumn.yhZjhm,idCardList);
        List<String> foundIdCards = userList.stream().map(BizPtyh::getYhZjhm).collect(Collectors.toList());
        for (String s : idCardList) {
            if (!foundIdCards.contains(s)){
                int rowNum = idCardRowNumMap.get(s);
                String error = errorMap.get(rowNum) + "身份证号不存在";
                errorMap.put(rowNum,error);
            }
        }

        for (BizPtyh ptyh : userList) {
            idCardYhIdMap.put(ptyh.getYhZjhm(),ptyh.getId());
        }

        // 同一个学员，同一个科目，只能交一次
        for (RowData row : rowDataList) {
            String yhId = idCardYhIdMap.get(row.getIdCard());
            SimpleCondition condition = new SimpleCondition(BizKsJf.class);
            condition.eq(BizKsJf.InnerColumn.kmId,row.getSubject());
            condition.eq(BizKsJf.InnerColumn.yhId,yhId);
            int count = countByCondition(condition);
            if (count > 0){
                int rowNum = idCardRowNumMap.get(row.getIdCard());
                String error = errorMap.get(rowNum) + "该学员已过科目" + row.getSubject()+"的费用";
                errorMap.put(rowNum,error);
            }
        }

        List<String> errors = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : errorMap.entrySet()) {
            errors.add("第"+entry.getKey()+"行："+entry.getValue());
        }
        if (errors.size() != 0){
            res.setResult(errors);
            res.setCode(100);
        }
        return res;
    }

    private class RowData{
        private String name;
        private String idCard;
        private Integer subject;
        private boolean isPayed;
        private String money;
        private String method;

        public RowData(List<String> row){
            this.name = row.get(0);
            this.idCard = row.get(1);
            this.subject = StringUtils.isEmpty(row.get(2)) ? null : Integer.parseInt(row.get(2));
            this.isPayed = "是".equals(row.get(3));
            this.money = row.get(4);
            this.method = row.get(5);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public Integer getSubject() {
            return subject;
        }

        public void setSubject(Integer subject) {
            this.subject = subject;
        }

        public boolean isPayed() {
            return isPayed;
        }

        public void setPayed(boolean payed) {
            isPayed = payed;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }
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
//        try {
//            // 2018/7/2  用户缴费、受理、约考 这些信息是否需要下发验证号码  经理2018-07-02 微信上说暂时不发
//            String res = wechatService.sendTemplateMsg(msg,null);
//            log.info("sendMsg result :", res);
//            return res;
//        } catch (WxErrorException e) {
//            log.error("发送微信模板消息异常", e);
//        }
        return "未知错误";
    }
}
