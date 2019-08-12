package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.app.service.AppUserService;
import com.cwb.platform.biz.model.BizOrder;
import com.cwb.platform.biz.model.BizUser;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户实名表
 */
@RestController
@RequestMapping("/app/user")
public class AppUserController extends AppUserBaseController {
    @Autowired
    private AppUserService service;
    /**
     * 我的团队
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizUser>> pager(BizOrder entity, Page<BizUser> pager){
        return service.pager(pager);
    }


    /**
     * 用户团队展示 筛选 进度
     * @param grade 等级
     * @param yhlx 用户类型
     * @param sfjf 是否缴费
     */
    @PostMapping("/myteam")
    public ApiResponse<List<BizUser>> myTeam(@RequestParam(value = "grade",required = false) String grade,
                                             @RequestParam(value = "yhlx",required = false) String yhlx,
                                             @RequestParam(value = "sfjf" ,required =  false) String sfjf,
                                             @RequestParam(value = "yhxm",required = false)String yhXm,
                                             @RequestParam(value =  "yhZt",required =  false)String yhZt,
                                             Page<BizUser> page){


         return  service.myTeam(grade, yhlx ,sfjf, yhXm, page);

    }




}
