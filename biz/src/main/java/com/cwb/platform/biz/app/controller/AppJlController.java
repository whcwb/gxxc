package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.app.service.AppJlService;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教练列表
 *
 */
@RestController
@RequestMapping("/app/jl")
public class AppJlController extends AppUserBaseController {
//public class JlController {
    @Autowired
    private AppJlService service;

    /**
     * 教练列表
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizJl>> pager(BizJl entity, Page<BizJl> pager){
        return service.pager(pager);
    }

    /**
     * 查询教练的学员列表
     */
    @GetMapping("/myStudent")
    public ApiResponse<String> getMyStudent(String jz, String xm, @RequestParam(defaultValue = "1") int pageNum ,@RequestParam(defaultValue = "8") int pageSize){
        return service.getMyStudent(jz, xm, pageNum, pageSize);
    }

    /**
     * 新查询
     */
    public ApiResponse<String> getMyStudentNew(String jz, String xm, @RequestParam(defaultValue = "1") int pageNum ,@RequestParam(defaultValue = "8") int pageSize){
        return service.getMyStudentNew(jz, xm, pageNum, pageSize);
    }

}
