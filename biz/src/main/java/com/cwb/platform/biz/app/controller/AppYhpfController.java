package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizYhpf;
import com.cwb.platform.biz.service.YhpfService;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户评分表
 */
@RestController
@RequestMapping("/app/pf")
public class AppYhpfController extends AppUserBaseController {
    @Autowired
    private YhpfService yhpfService;

    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizYhpf entity){
        return yhpfService.validAndSave(entity);
    }

//    query(entity)

    /**
     * 获取教练员的评分
     * @param jlId
     * @return
     */
    @RequestMapping(value="/getjlpf", method={RequestMethod.POST})
    public ApiResponse<BizYhpf> getUserCoach(@RequestParam("jlId") String jlId){
        return yhpfService.getUserCoach(jlId);
    }


    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizYhpf>> pager(BizYhpf entity, Page<BizYhpf> pager){
        return yhpfService.pager(pager);
    }

}
