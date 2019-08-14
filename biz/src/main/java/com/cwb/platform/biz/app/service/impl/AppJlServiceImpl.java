package com.cwb.platform.biz.app.service.impl;


import com.cwb.platform.biz.app.service.AppJlService;
import com.cwb.platform.biz.mapper.BizJlMapper;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.model.BizYjmx;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.sys.mapper.SysYhJsMapper;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYhJs;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppJlServiceImpl extends BaseServiceImpl<BizJl, String> implements AppJlService {
    @Autowired
    private BizJlMapper entityMapper;
    @Autowired
    private SysYhJsMapper jsService;
    @Autowired
    private PtyhService ptyhService;


    @Override
    protected Mapper<BizJl> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return BizYjmx.class;
    }

    /**
     * 分页补充
     *
     * @param condition
     * @return
     */
    @Override
    public boolean fillPagerCondition(LimitedCondition condition) {
        return true;
    }


    @Override
    public ApiResponse<String> getMyStudent(String jz, String xm, int pageNum, int pageSize) {
        BizPtyh user = getAppCurrentUser();
        RuntimeCheck.ifNull(user, "未找到用户信息");
        Map<String, String> m = new HashMap<>();
        m.put("slzy", "0");
        m.put("k1", "1");
        m.put("k2", "2");
        m.put("k3", "3");
        m.put("k4", "4");
        // 根据用户id 查到跟他有关的所有学员
//        BizPtyh user = ptyhService.findById("605772682100736000");
        if (StringUtils.isBlank(xm)) {
            xm = null;
        }
        if (StringUtils.isBlank(jz)) {
            jz = null;
        }
        SimpleCondition co = new SimpleCondition(SysYhJs.class);
        co.eq(SysYhJs.InnerColumn.yhId, user.getId());
        List<SysYhJs> js = jsService.selectByExample(co);
        List<String> list = js.stream().map(SysYhJs::getJsId).collect(Collectors.toList());
        List<String> dqzts = new ArrayList<>();
        list.forEach(s -> dqzts.add(m.get(s)));

        String finalJz = jz;
        String finalXm = xm;
        PageInfo<BizUser> bizUsers =
                PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> entityMapper.getMyStudent(user.getId()
                        , finalXm, finalJz, dqzts));

        bizUsers.getList().forEach(bizUser -> {
            String dqzt = bizUser.getYhDqzt();
            if (dqzt.charAt(0) == '0') {
                bizUser.setYhDqzt("0");
            } else if (dqzt.charAt(0) == '1') {
                bizUser.setYhDqzt("1");
            } else if (dqzt.charAt(0) == '2') {
                bizUser.setYhDqzt("2");
            } else if (dqzt.charAt(0) == '3') {
                bizUser.setYhDqzt("3");
            } else {
                bizUser.setYhDqzt("4");
            }
        });
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(bizUsers);
        return res;
    }
}
