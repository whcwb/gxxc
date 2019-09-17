package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.model.BizSubSchool;
import com.cwb.platform.biz.model.BizTrainPlace;
import com.cwb.platform.biz.service.SubSchoolService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/app/subschool")
public class AppSubSchoolController extends QueryController<BizSubSchool, String> {
    @Autowired
    private SubSchoolService service;

    @Override
    protected BaseService<BizSubSchool, String> getBaseService() {
        return service;
    }

    /**
     * 获取代培点下面所有的训练场
     */
    @GetMapping("/getAllTrainPlace")
    public ApiResponse<List<BizTrainPlace>> getAllTrainPlace(String id){
        return service.getAllTrainPlace(id);
    }
}
