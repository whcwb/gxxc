package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizCjdMapper;
import com.cwb.platform.biz.model.BizCjd;
import com.cwb.platform.biz.service.CjdService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.common.Mapper;

@Service
public class CjdServiceImpl extends BaseServiceImpl<BizCjd,String> implements CjdService {

    @Autowired
    private BizCjdMapper entityMapper;

    @Override
    protected Mapper<BizCjd> getBaseMapper() {
        return entityMapper;
    }


    /**
     * 用户上传传成绩单 , 对教练进行评价
     * @param bizCjd
     * @return
     */
    @Override
    public ApiResponse<String> giveMark(BizCjd bizCjd) {

        BizPtyh bizPtyh  = getAppCurrentUser(false); // false 表示当前用户未登录仍然可以使用
         BizCjd entity = new BizCjd();

        if(!ObjectUtils.isEmpty(bizPtyh)){  // 当前登录用户
            entity.setXyId(bizPtyh.getId());
            entity.setPfCjr(bizPtyh.getId());
            entity.setPfLy("0"); // 0 为 app端
        }else{
            entity.setPfLy("1"); // 1 为 web端
        }

        RuntimeCheck.ifBlank(bizCjd.getJlId() , "教练id不能为空");
        RuntimeCheck.ifBlank(bizCjd.getXySfhg(), "学员是否合格不能为空");
        if(StringUtils.containsNone(bizCjd.getXySfhg(),'0','1')){
            return ApiResponse.fail("学员合格信息填写有误");
        }
        RuntimeCheck.ifBlank(bizCjd.getKmBm(), "科目编码不能为空");
        RuntimeCheck.ifBlank(bizCjd.getImgUrl(), "图片不能为空");
        RuntimeCheck.ifBlank(bizCjd.getJlPf(), "教练评分不能为空");

        BeanUtils.copyProperties(bizCjd,entity,"xyId","pfCjr","pfLy");
        entity.setPfCjsj(DateUtils.getNowTime());
        entity.setId(genId());
        int i = save(entity);

        return i==1?ApiResponse.success():ApiResponse.fail("上传失败");
    }
}
