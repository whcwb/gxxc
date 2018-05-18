package com.cwb.platform.biz.service.impl;


import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.model.BizPtyh;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class PtyhServiceImpl extends BaseServiceImpl<BizPtyh,java.lang.String> implements PtyhService{

    @Value("${img_url}")
    private String imgUrl;

    @Autowired
    private BizPtyhMapper entityMapper;

    @Override
    protected Mapper<BizPtyh> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return BizPtyh.class;
    }


    @Override
    protected void afterPager(PageInfo<BizPtyh> resultPage){
        List<BizPtyh> list=resultPage.getList();
        if(CollectionUtils.isNotEmpty(list)){

            list.stream().forEach(bizPtyh ->afterReturn(bizPtyh));

        }

        return;
    }

    /**
     * 重置学院部分信息 ， 不对外展示
     * @param bizPtyh
     */
    private void afterReturn(BizPtyh bizPtyh) {
        bizPtyh.setYhMm("");
        bizPtyh.setYhOpenId("");
        bizPtyh.setYhAlipayId("");
        if(StringUtils.isNotBlank(bizPtyh.getYhZjhm())){
            bizPtyh.setYhZjhm(bizPtyh.getYhZjhm().replaceAll("(\\d{3})\\d*(\\d{4})","$1******$2"));
        }
        if(StringUtils.isNotBlank(bizPtyh.getYhTx()) && StringUtils.containsNone(bizPtyh.getYhTx(),"http")){
            bizPtyh.setYhTx(imgUrl + bizPtyh.getYhTx());
        }
    }

    /**
     *  更新用户是否锁定状态 0 否 1 是
     *
     * @param bizPtyh
     * @return
     */
    @Override
    public ApiResponse<String> updateSfsd(BizPtyh bizPtyh) {
        RuntimeCheck.ifBlank(bizPtyh.getId(),"用户Id不能为空");
        RuntimeCheck.ifBlank(bizPtyh.getYhSfsd(),"用户锁定状态不能为空");
        if(StringUtils.containsNone(bizPtyh.getYhSfsd(), new char[]{'0', '1'})){
            return ApiResponse.fail("请输入正确的状态");
        }
        int i = update(bizPtyh);
        return i==1?ApiResponse.success():ApiResponse.fail();
    }

    /**
     * 更新用户是否分配信息
     * @param bizPtyh
     * @return
     */
    @Override
    public ApiResponse<String> updateSffp(BizPtyh bizPtyh) {
        RuntimeCheck.ifBlank(bizPtyh.getId(),"用户Id不能为空");
        RuntimeCheck.ifBlank(bizPtyh.getYhIxySffp(),"用户是否分配不能为空");
        if(StringUtils.equals(bizPtyh.getYhIxySffp(),"0")){
            bizPtyh.setYhFpms("");
        }else if(StringUtils.equals(bizPtyh.getYhIxySffp(),"1")){
            if(StringUtils.isBlank(bizPtyh.getYhFpms())){
                return ApiResponse.fail("用户分配描述不能为空");
            }
        }
        int i = update(bizPtyh);
        return i==1?ApiResponse.success():ApiResponse.fail();
    }
}
