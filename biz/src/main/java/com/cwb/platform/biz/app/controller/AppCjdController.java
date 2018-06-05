package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.app.bean.StudentListModel;
import com.cwb.platform.biz.model.BizCjd;
import com.cwb.platform.biz.service.CjdService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 考试成绩上传
 *
 */
@RestController
@RequestMapping("/app/cjd")
public class AppCjdController extends AppUserBaseController{
    @Autowired
    private CjdService service;

    /**
     * 用户上传成绩单
     *
     */
    @PostMapping("/save")
    public ApiResponse<String> giveMark(BizCjd bizCjd){
        BizPtyh ptyh= getAppCurrentUser();
        String yhLx=ptyh.getYhLx();
        RuntimeCheck.ifTrue(StringUtils.equals(yhLx,"2"),"您好，非教练不能上传成绩单");
        return service.giveMark(bizCjd);
    }

    /**
     * 查询学员成绩单
     * @param  xyid 学员ID
     */
    @PostMapping("/getxy")
    public ApiResponse<Map<String,Object>> getUserMessage(String xyid){
        RuntimeCheck.ifNull(xyid,"您好，请选择学员");
        return service.getUserMessage(xyid);
    }
    /**
     *  我的学员列表  分页查询
     *  http://192.168.31.38:8080/#/myStudent
     */
    @PostMapping("/getcjblist")
    public ApiResponse<PageInfo<StudentListModel>> getBizCjbList(Page<StudentListModel> ptyhPage,String xyZt){
        return service.getBizCjbList(ptyhPage,xyZt);
    }

}
