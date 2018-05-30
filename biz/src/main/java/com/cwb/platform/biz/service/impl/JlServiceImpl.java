package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizJlMapper;
import com.cwb.platform.biz.mapper.BizPtyhMapper;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.service.JlService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.sys.model.SysYh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
@Service
public class JlServiceImpl extends BaseServiceImpl<BizJl,String> implements JlService {

    @Autowired
    private BizJlMapper entityMapper;
    @Autowired
    private BizPtyhMapper bizPtyhMapper;

    @Autowired
    private PtyhService ptyhService;
    @Override
    protected Mapper<BizJl> getBaseMapper() {
        return entityMapper;
    }
    /**
     * 更新教练认证状态
     *
     * yhzt 1成功  2 失败
     *
     * @return
     */
    @Override
    public ApiResponse<String> updateYhRz(BizPtyh obd){
        SysYh sysYh = getCurrentUser();
        BizPtyh user = bizPtyhMapper.selectByPrimaryKey(obd.getId());
        if (user == null) return ApiResponse.fail("用户不存在");

        RuntimeCheck.ifTrue(!StringUtils.equals(user.getYhLx(), "2"), "操作失败，只有教练才能进行认证操作");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhZt(), "1"), "操作失败，该教练已认证无需再次认证");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhSfsd(), "1"), "操作失败，该教练已锁定无法进行认证操作");
        RuntimeCheck.ifTrue(StringUtils.equals(user.getYhSfyjz(), "1"), "操作失败，该教练无驾照无法进行认证操作");

        RuntimeCheck.ifBlank(obd.getYhZt(), "审核状态不能为空");
        if (StringUtils.containsNone(obd.getYhZt(), new char[]{'1', '2'})) {
            return ApiResponse.fail("请输入正确审核状态");
        }

        BizPtyh newEntity = new BizPtyh();
        newEntity.setId(user.getId());
        newEntity.setYhZt(obd.getYhZt());
        String yhZtMs=" ";
        if(StringUtils.equals("2",obd.getYhZt())){
            yhZtMs=obd.getYhZtMs();
        }
        newEntity.setYhZtMs(yhZtMs);
       int i = ptyhService.update(newEntity);
        return i==1?ApiResponse.success():ApiResponse.fail("审核失败");
    }
}
