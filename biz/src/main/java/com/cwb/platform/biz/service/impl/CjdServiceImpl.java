package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.app.bean.StudentListModel;
import com.cwb.platform.biz.mapper.BizCjdMapper;
import com.cwb.platform.biz.model.BizCjd;
import com.cwb.platform.biz.service.CjdService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.MathUtil;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CjdServiceImpl extends BaseServiceImpl<BizCjd,String> implements CjdService {

    @Value("${SUBJECT_MARK_1:90}")
    private String subjectMark1;
    @Value("${SUBJECT_MARK_2:90}")
    private String subjectMark2;
    @Value("${SUBJECT_MARK_3:90}")
    private String subjectMark3;
    @Value("${SUBJECT_MARK_4:90}")
    private String subjectMark4;

    @Autowired
    private BizCjdMapper entityMapper;

    @Autowired
    private PtyhService ptyhService;


    @Override
    protected Mapper<BizCjd> getBaseMapper() {
        return entityMapper;
    }


    /**
     * 用户上传传成绩单
     * @param bizCjd
     * @return
     */
    @Override
    public ApiResponse<String> giveMark(BizCjd bizCjd) {
//        1、验证入参
        BizPtyh userRequest = getAppCurrentUser();
        RuntimeCheck.ifBlank(bizCjd.getKmBm(), "科目编码不能为空");
        RuntimeCheck.ifBlank(bizCjd.getXyId(), "请选择一位学员进行上传");
        RuntimeCheck.ifBlank(bizCjd.getImgUrl(), "图片不能为空");
        RuntimeCheck.ifBlank(bizCjd.getXyCj(), "学员考试成绩不能为空");
        String xySfhg="0";//学员是否合格  0：不合格 1：合格
        String kmBm=bizCjd.getKmBm();//科目编码
        String xyCj=bizCjd.getXyCj();//学员考试成绩
        if(StringUtils.equals(kmBm,"1")){
            if(MathUtil.stringToDouble(xyCj)>MathUtil.stringToDouble(subjectMark1)){
                xySfhg="1";
            }
        }else if(StringUtils.equals(kmBm,"2")){
            if(MathUtil.stringToDouble(xyCj)>MathUtil.stringToDouble(subjectMark2)){
                xySfhg="1";
            }
        }else if(StringUtils.equals(kmBm,"3")){
            if(MathUtil.stringToDouble(xyCj)>MathUtil.stringToDouble(subjectMark3)){
                xySfhg="1";
            }
        }else if(StringUtils.equals(kmBm,"4")){
            if(MathUtil.stringToDouble(xyCj)>MathUtil.stringToDouble(subjectMark4)){
                xySfhg="1";
            }
        }
//        2、删除原始成绩单
        BizCjd delEntity = new BizCjd();
        delEntity.setXyId(bizCjd.getXyId());
        delEntity.setKmBm(bizCjd.getKmBm());
        delEntity.setJlId(bizCjd.getJlId());
        entityMapper.delete(delEntity);
//        3、上传新成绩单
        BizCjd entity = new BizCjd();
        BeanUtils.copyProperties(bizCjd,entity);
        entity.setJlId(userRequest.getId());
        entity.setXySfhg(xySfhg);//学员是否合格  0：不合格 1：合格
        entity.setId(genId());
        entity.setXySfhg(xySfhg);
        int i = save(entity);
        return i==1?ApiResponse.success():ApiResponse.fail("上传失败");
    }
    /**
     * 查询学员成绩单
     * @param  xyid 学员ID
     */
    @Override
    public ApiResponse<Map<String,Object>> getUserMessage(String xyid){
        SimpleCondition condition = new SimpleCondition(BizCjd.class);
        BizPtyh user = getAppCurrentUser();
        String yhLx=user.getYhLx();
        if(!StringUtils.equals(yhLx,"2")){//不等于教练
            xyid=user.getId();
        }else{
            condition.like(BizCjd.InnerColumn.jlId.name(), user.getId());//教练ID
        }

        Map<String,Object> ret= new HashMap<String,Object>();
        BizPtyh ptyh=ptyhService.findByIdSelect(xyid);
        RuntimeCheck.ifTrue(ptyh == null, "用户资料有误！");
        ret.put("yhbm",ptyh.getYhBm());//用户别名
        ret.put("yhxm",ptyh.getYhXm());//用户姓名
        ret.put("yhzh",ptyh.getYhZh());//用户账户
        ret.put("yhTx",ptyh.getYhTx());//用户头像

        //  根据用户ID查询出自己的银行卡
        condition.like(BizCjd.InnerColumn.xyId.name(), xyid);
        condition.setOrderByClause( BizCjd.InnerColumn.kmBm.desc());
        List<BizCjd> bizJls = this.findByCondition(condition);

        ret.put("markList",bizJls);//学员考试成绩图片
        return ApiResponse.success(ret);
    }

   public ApiResponse<PageInfo<StudentListModel>> getBizCjbList(Page<StudentListModel> ptyhPage){
       PageInfo<StudentListModel> pageInfo = new PageInfo<>();
       // 获取当前登录用户
       BizPtyh user = getAppCurrentUser();
       SimpleCondition condition = new SimpleCondition(StudentListModel.class);
       condition.eq(StudentListModel.InnerColumn.yhJlid.name(), user.getId());
// todo 填写参数值
       //condition.like(StudentListModel.InnerColumn.xyZt.name(), "%" + ptyhPage.get + "%");


       condition.eq(StudentListModel.InnerColumn.yhJlid.name(), user.getId());

//       pageInfo = findPage(ptyhPage,condition);

       return ApiResponse.success(pageInfo);
   }
}
