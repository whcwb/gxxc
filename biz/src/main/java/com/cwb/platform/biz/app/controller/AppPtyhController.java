package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.app.service.AppUserService;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.util.Base64TestUtil;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.SimpleCondition;
import com.cwb.platform.util.commonUtil.FileUtil;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.Page;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 前台 平台用户
 */
@RestController
@RequestMapping("/app/ptyh")
public class AppPtyhController extends AppUserBaseController {
    @Autowired
    private PtyhService service;
    @Value("${qr_code_file_url}")
    private String qrCodeFileUrl;

    @Autowired
    private AppUserService userService;



    @RequestMapping("getStudentCoach")
    public ApiResponse<BizJl> getStudentCoach(String yhId){
        return service.getStudentCoach(yhId);
    }


    /**
     * 用户注册
     * @param entity
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizPtyh entity,HttpServletRequest request) throws IOException, WxErrorException {
//        String openId=request.getHeader("openid");
//        entity.setYhOpenId(openId);
        return service.userEnroll(entity,request);
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
     * @return
     */
    @RequestMapping(value="/updatesm", method={RequestMethod.POST})
    public ApiResponse<String> updateUserReal(BizPtyh entity) throws WxErrorException, IOException {
        return service.updateUserReal(entity);
    }

    @RequestMapping(value = "/bindOpenId")
    public ApiResponse<String> bindOpenId(String openid){
        return service.bindOpenId(openid);
    }


    /**
     * 个人资料查询	获取登录人的头像、姓名(别名)
     * @return
     */
    @RequestMapping(value="/get", method={RequestMethod.POST})
    public ApiResponse<BizPtyh> get(){
        BizPtyh user = getAppCurrentUser();
        BizPtyh users = service.findByIdSelect(user.getId());
        if(user!=null){
            //查询该记录邀请的用户总数
//            BizUser userDetail=new BizUser();
//            userDetail.setYhSjid(user.getId());

            SimpleCondition condition = new SimpleCondition(BizUser.class);
            condition.and().andCondition(" ( YH_SJID='"+user.getId()+"' OR YH_SSJID='"+user.getId()+"') ");
            long userInviteCount=0;
            userInviteCount=userService.countByCondition(condition);
            users.setUserInviteCount(userInviteCount);


            if (StringUtils.equals(users.getYhXySlType(), "0") ||
                    StringUtils.equals(users.getYhXySlType(), "1") ||
                    StringUtils.equals(users.getYhXySlType(), "2") ||
                    StringUtils.equals(users.getYhXySlType(), "3") ||
                    StringUtils.equals(users.getYhXySlType(), "5") ||
                    StringUtils.equals(users.getYhXySlType(), "4")
                    ) {
                users.setYhDqzt("0");
            }
            if (users.getYhXyYkType().charAt(0) == '0') {
                users.setYhDqzt("0");
            } else if (users.getYhXyYkType().charAt(0) == '1') {
                users.setYhDqzt("1");
            } else if (users.getYhXyYkType().charAt(0) == '2') {
                users.setYhDqzt("2");
            } else if (users.getYhXyYkType().charAt(0) == '3') {
                users.setYhDqzt("3");
            } else {
                users.setYhDqzt("4");
            }

            //如果本阶段已完成，自动跳转到下一个阶段
            if(StringUtils.equals(users.getYhXyYkType(),"11")){
                users.setYhDqzt("2");
            }else if(StringUtils.equals(users.getYhXyYkType(),"21")){
                users.setYhDqzt("3");
            }else if(StringUtils.equals(users.getYhXyYkType(),"31")){
                users.setYhDqzt("4");
            }else if(StringUtils.equals(users.getYhXySlType(),"5")){
                users.setYhDqzt("1");
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
    public ApiResponse<List<BizPtyh>> getBizPtyhList(Page<BizPtyh> ptyhPage){
        return service.getBizPtyhList(ptyhPage);
    }

    /**
     * 学员修改拥有驾照
     */
    @PostMapping("/updatesfyjz")
    public ApiResponse<String> updateJz(BizPtyh entity){
        return service.updateJz(entity);
    }

    /**
     * 查询用户受理专员列表
     */
    @PostMapping("/getJls")
    public ApiResponse<List<BizJl>> getJls(String yhId){
        return service.getJls(yhId);
    }



    /**
     * 上传用户签名
     */
    @PostMapping("/getqm")
    public ApiResponse<String> base64UpLoad(@RequestParam String base64Data){
        BizPtyh user = getAppCurrentUser();

        if(base64Data == null || "".equals(base64Data)){
            return ApiResponse.fail("上传失败，上传图片数据为空");
//            throw new Exception("上传失败，上传图片数据为空");//
        }

        BizPtyh queryBizPtyh=service.findByIdSelect(user.getId());
        if(queryBizPtyh==null){
            return ApiResponse.fail("操作失败，请重新尝试");
        }
//        RuntimeCheck.ifTrue(StringUtils.isNotBlank(queryBizPtyh.getYhAutograph()),"用户");
        //qrCodeFileUrl
        String fileUrl="/user_autograph/"+(UUID.randomUUID().toString()).replaceAll("-","")+".png";
        FileUtil.fileExistsDir(qrCodeFileUrl+"/user_autograph/");
        Boolean type=Base64TestUtil.generateImage(base64Data,qrCodeFileUrl+fileUrl);
        if(type){
            /*BizPtyh newPtyh=new BizPtyh();
            newPtyh.setYhAutograph(fileUrl);
            newPtyh.setId(user.getId());
            service.update(newPtyh);*/
            ApiResponse<String> res = new ApiResponse<>();
            res.setMessage("操作成功");
            res.setResult(fileUrl);
            return res;
        }else{
            return ApiResponse.fail("上传失败请重新尝试");
        }
    }


    /**
     * app扫码查询邀请码接口
     */
    @PostMapping("/getCode")
    public ApiResponse<String> getCode(String qrcode){
        List<BizPtyh> ptyhs = service.findEq(BizPtyh.InnerColumn.qrcode, qrcode);
        RuntimeCheck.ifTrue(CollectionUtils.isEmpty(ptyhs), "未找到用户信息");
        BizPtyh ptyh = ptyhs.get(0);
        return ApiResponse.success(ptyh.getYhZsyqm());
    }



}
