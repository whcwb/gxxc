package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 前台 平台用户
 */
@RestController
@RequestMapping("/app/ptyh")
public class AppPtyhController extends AppUserBaseController {
    @Autowired
    private PtyhService service;
    @Autowired
    private WjService wjService;

    /**
     * 用户注册
     * @param entity
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizPtyh entity){
        return service.userEnroll(entity);
    }
    /**
     * 修改登录密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return 操作结果
     */
    @RequestMapping(value = "/mdfPwd",method = RequestMethod.POST)
    public ApiResponse<String> updateMdfPwd(@RequestParam(name = "oldPwd")String oldPwd,
                                      @RequestParam(name = "newPwd")String newPwd){
//        RuntimeCheck.ifTrue(( StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd) ||  StringUtils.isEmpty(secPwd)),"请输入密码");
//        RuntimeCheck.ifTrue(!newPwd.equals(secPwd),"两次输入密码不一致");
        BizPtyh user = getAppCurrentUser();
        RuntimeCheck.ifTrue(user == null,"请重启登录！");
        return service.mdfPwd(user.getId(),oldPwd,newPwd);
    }

    /**
     * 微信登录
     * @param entity
     * @return
     */
    @RequestMapping(value="/wxlogin", method={RequestMethod.POST})
    public ApiResponse<Map<String,Object>> wxlogin(BizPtyh entity){
        String openId="";
        //获取用户的OPENID
        return service.wxlogin(openId);
    }

    /**
     * 用户头像修改
     * @param entity
     * yhTx  头像
     * yhBm  用户别名
     * @return
     */
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(BizPtyh entity){
        return service.updateUserInfo(entity);
    }

    /**
     * 用户实名操作
     * @param entity
     * yhXm     用户姓名
     * yhZjhm     用户证件号码
     * yhXb     用户性别
     * yhSfyjz     用户驾照状态不能为空
     * imgList 以,进行分隔
     * imgTypeList 以,进行分隔
     *
     * @return
     */
    @RequestMapping(value="/updatesm", method={RequestMethod.POST})
    public ApiResponse<String> updateUserReal(BizPtyh entity){
        return service.updateUserReal(entity);
    }

    /**
     * 个人资料查询	获取登录人的头像、姓名(别名)
     * @return
     */
    @RequestMapping(value="/get", method={RequestMethod.POST})
    public ApiResponse<BizPtyh> get(){
        BizPtyh user = getAppCurrentUser();
        BizPtyh users = service.findByIdSelect(user.getId());
        //判断一下，这个用户有没有上传实名资料 没有上传就给前台-1，让前台提示用户去实名。
        if(users!=null){
            //认证状态 ZDCLK0043(0 未认证、1 已认证 2、认证失败)
            String yhZt=users.getYhZt();
            if(StringUtils.equals(yhZt,"0")&&StringUtils.equals("1",user.getYhLx())){
                BizWj realName=new BizWj();
                realName.setYhId(users.getId());
                int i=wjService.countByEntity(realName);
                if(i<1){
                    users.setYhZt("-1");
                }
            }
            String yhJlsh=user.getYhJlsh();//教练认证状态 ZDCLK0043(0 未认证、1 已认证 2、认证失败)
            if(StringUtils.equals(yhJlsh,"0")&&StringUtils.equals("2",user.getYhLx())){
                BizWj realName=new BizWj();
                realName.setYhId(users.getId());
                int i=wjService.countByEntity(realName);
                if(i<1){
                    users.setYhJlsh("-1");
                }
            }

        }
        return ApiResponse.success(users);
    }
    /**
     * 我的邀请码
     * 用户缴费成功后，为用户生成邀请码，未缴费引导用户缴费。
     * @return
     */
    @RequestMapping(value="/getuser", method={RequestMethod.POST})
    public ApiResponse<BizPtyh> getUserInvitationCode (){
        BizPtyh user = getAppCurrentUser();
        BizPtyh users = service.getUserInvitationCode(user.getId());
        return ApiResponse.success(users);
    }

    /**
     * 用户申请教练
     */
    @PostMapping("/updatelx")
    public ApiResponse<String> updatelx(BizJl bizJl){
        return service.updatelx(bizJl);
    }
    /**
     *  app端 显示列表
     */
    @PostMapping("/ptyhlist")
    public ApiResponse<PageInfo<BizPtyh>> getBizPtyhList(Page<BizPtyh> ptyhPage){
        return service.getBizPtyhList(ptyhPage);
    }

    /**
     * 学员修改拥有驾照
     */
    @PostMapping("/updatesfyjz")
    public ApiResponse<String> updateJz(BizPtyh entity){
        return service.updateJz(entity);
    }


}
