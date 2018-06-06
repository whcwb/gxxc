package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.biz.service.UserService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController extends QueryController<BizUser,String> {
//public class UserController {
    @Autowired
    private UserService service;

    @Override
    protected BaseService<BizUser, String> getBaseService() {
        return service;
    }

    /**
     * 展示教练的学员列表
     */
    @PostMapping("/getStudentList")
    public ApiResponse<PageInfo<BizUser>> getStudentList(@RequestParam("yhid")String yhid, @RequestParam(value = "xyzt",required = false)String xyZt, Page<BizUser> page){
        return service.getStudentList(yhid,xyZt, page);
    }



}
