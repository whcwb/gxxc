package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizKsJfMapper;
import com.cwb.platform.biz.model.BizKsJf;
import com.cwb.platform.biz.model.BizKsJf;
import com.cwb.platform.biz.model.BizKsSl;
import com.cwb.platform.biz.model.BizKsYk;
import com.cwb.platform.biz.service.KsYkService;
import com.cwb.platform.biz.service.KsjfService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.text.ParseException;
import java.util.*;

/**
 * 学员考试缴费记录表
 * Created by Administrator on 2018/6/19.
 */
@Service
public class KsjfServiceImpl extends BaseServiceImpl<BizKsJf,String> implements KsjfService {
    @Autowired
    private BizKsJfMapper entityMapper;
    @Autowired
    private PtyhService ptyhService;
    @Autowired
    private KsYkService ykService;


    @Override
    protected Mapper<BizKsJf> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizKsJf.class;
    }

    @Override
    public ApiResponse<String> validAndSave(BizKsJf entity) {
        int i=save(entity);
        return i==1?ApiResponse.success():ApiResponse.fail();
    }
    @Override
    public int save(BizKsJf entity) {
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

    @Override
    public ApiResponse<Map<String,String>> getPayInfo(String yhId) {
        RuntimeCheck.ifBlank(yhId,"请选择用户");
        SimpleCondition condition1 = new SimpleCondition(BizKsJf.class);
        condition1.setOrderByClause("cjsj desc");
        condition1.eq(BizKsJf.InnerColumn.yhId,yhId);
        condition1.eq(BizKsJf.InnerColumn.kmId,"1");

        SimpleCondition condition2 = new SimpleCondition(BizKsJf.class);
        condition2.setOrderByClause("cjsj desc");
        condition2.eq(BizKsJf.InnerColumn.yhId,yhId);
        condition2.eq(BizKsJf.InnerColumn.kmId,"2");

        SimpleCondition condition3 = new SimpleCondition(BizKsJf.class);
        condition3.setOrderByClause("cjsj desc");
        condition3.eq(BizKsJf.InnerColumn.yhId,yhId);
        condition3.eq(BizKsJf.InnerColumn.kmId,"3");

        SimpleCondition condition4 = new SimpleCondition(BizKsJf.class);
        condition4.setOrderByClause("cjsj desc");
        condition4.eq(BizKsJf.InnerColumn.yhId,yhId);
        condition4.eq(BizKsJf.InnerColumn.kmId,"4");


        List<BizKsJf> list1 = entityMapper.selectByExampleAndRowBounds(condition1,new RowBounds(0,1));
        List<BizKsJf> list2 = entityMapper.selectByExampleAndRowBounds(condition2,new RowBounds(0,1));
        List<BizKsJf> list3 = entityMapper.selectByExampleAndRowBounds(condition3,new RowBounds(0,1));
        List<BizKsJf> list4 = entityMapper.selectByExampleAndRowBounds(condition4,new RowBounds(0,1));

        Map<String,BizKsJf> payMap = new HashMap<>(4);
        if (list1.size() != 0){
            payMap.put("1",list1.get(0));
        }
        if (list2.size() != 0){
            payMap.put("2",list2.get(0));
        }
        if (list3.size() != 0){
            payMap.put("3",list3.get(0));
        }
        if (list4.size() != 0){
            payMap.put("4",list4.get(0));
        }

        ApiResponse<Map<String,BizKsYk>> examListRes = ykService.getUserExamInfo(yhId);
        if (examListRes.getResult() == null)return ApiResponse.success(new HashMap<>());
        Map<String,BizKsYk> examMap = examListRes.getResult();
        Map<String,String> map = new HashMap<>(4);

        try {
            for (Map.Entry<String, BizKsYk> entry : examMap.entrySet()) {
                BizKsJf payInfo = payMap.get(entry.getKey());
                if (payInfo == null){
                    map.put(entry.getKey(),"未缴");
                }else{
                    // 如果有缴费记录，判断考试是否通过，如果考试通过，则已缴费；如果考试未通过，则判断缴费时间
                    BizKsYk exam = entry.getValue();
                    if (exam.getCj1() > 90 || exam.getCj2() > 90){
                        map.put(entry.getKey(),"已缴");
                    }else{
                        Date yksj = DateUtils.getDate(exam.getYkSj(),"yyyy-MM-dd");
                        Date jfsj = DateUtils.getDate(exam.getYkSj(),"yyyy-MM-dd");
                        if (yksj.getTime() > jfsj.getTime()){
                            map.put(entry.getKey(),"已缴");
                        }else{
                            map.put(entry.getKey(),"未缴");
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ApiResponse.success(map);
    }
}
