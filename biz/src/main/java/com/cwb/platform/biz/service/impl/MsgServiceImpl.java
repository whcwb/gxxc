package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizMsgMapper;
import com.cwb.platform.biz.model.BizMsg;
import com.cwb.platform.biz.service.MsgService;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class MsgServiceImpl extends BaseServiceImpl<BizMsg,String> implements MsgService {

    @Autowired
    private BizMsgMapper entityMapper;

    @Autowired
    private PtyhService ptyhService;

    @Override
    protected Mapper<BizMsg> getBaseMapper() {
        return entityMapper;
    }

    @Override
    public ApiResponse<String> getUserMsg(int pageNum, int pageSize) {
        BizPtyh user = getAppCurrentUser();
        RuntimeCheck.ifNull(user, "未找到用户信息");
        SimpleCondition condition = new SimpleCondition(BizMsg.class);
        condition.eq(BizMsg.InnerColumn.userId, user.getId());
        condition.setOrderByClause(" CJSJ DESC");
        PageInfo<BizMsg> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            findByCondition(condition);
        });
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<String> reply(String content) {
        RuntimeCheck.ifBlank(content, "回复内容不能为空");
        BizPtyh user = getAppCurrentUser();
        RuntimeCheck.ifNull(user, "未找到用户信息");
        BizMsg msg = new BizMsg();
        msg.setCjsj(DateUtils.getNowTime());
        msg.setId(genId());
        msg.setContent(content);
        msg.setType("0");
        msg.setUserId(user.getId());
        msg.setUserName(user.getYhXm());
        save(msg);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse<String> getUserList(int pageNum, int pageSize,String type) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userName = request.getParameter("userName");
        PageInfo<BizMsg> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            if(StringUtils.isBlank(userName)){
                entityMapper.getUserList(type,null);
            }else{
                entityMapper.getUserList(type,userName);
            }

        });
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<String> getUserMsgs(int pageNum, int pageSize, String userId) {
       RuntimeCheck.ifBlank(userId, "请上传用户id");
       SimpleCondition condition = new SimpleCondition(BizMsg.class);
       condition.eq(BizMsg.InnerColumn.userId,userId);
       condition.setOrderByClause(" CJSJ DESC");
        PageInfo<BizMsg> info = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> findByCondition(condition));
//        Page<BizMsg> page = new Page<>();
//        page.setPageNum(pageNum);
//        page.setPageSize(pageSize);
        ApiResponse<String> res = new ApiResponse<>();
        res.setPage(info);
        return res;
    }

    @Override
    public ApiResponse<String> replyUser(String userId, String content) {
        RuntimeCheck.ifBlank(userId, "请选择需要回复的内容");
        RuntimeCheck.ifBlank(content, "回复内容不能为空");
        BizPtyh ptyh = ptyhService.findById(userId);
        RuntimeCheck.ifNull(ptyh, "未找到用户信息");
        BizMsg msg = new BizMsg();
        msg.setId(genId());
        msg.setUserName(ptyh.getYhXm());
        msg.setUserId(userId);
        msg.setType("1");
        msg.setContent(content);
        msg.setCjsj(DateUtils.getNowTime());
        save(msg);
        return ApiResponse.success();
    }
}
