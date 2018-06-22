package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizDriversSchool;
import com.cwb.platform.biz.model.BizExamPlace;
import com.cwb.platform.biz.model.BizTrainPlace;
import com.cwb.platform.biz.service.ExamPlaceService;
import com.cwb.platform.biz.service.SchoolService;
import com.cwb.platform.biz.service.TrainPlaceService;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 场地信息
 */
@RestController
@RequestMapping("/app/cd")
public class AppAeaController extends AppUserBaseController {
    //训练场表
    @Autowired
    private TrainPlaceService trainPlaceService;
    @Autowired
    private ExamPlaceService examPlaceService;
    @Autowired
    private SchoolService schoolService;


//    biz_drivers_school		驾校表		lat,lng
//    biz_exam_place			考场信息	lat,lng
//    biz_train_place			训练场表  经度,纬度  BizTrainPlace
    //训练场表
    @RequestMapping(value="/xlcpager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizTrainPlace>> pager(BizTrainPlace entity, Page<BizTrainPlace> pager){
        return trainPlaceService.pager(pager);
    }
//考场信息
    @RequestMapping(value="/kcpager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizExamPlace>> kcpager(BizExamPlace entity, Page<BizExamPlace> pager){
        return examPlaceService.pager(pager);
    }
    //驾校表
    @RequestMapping(value="/jxcpager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizDriversSchool>> jxpager(BizDriversSchool entity, Page<BizDriversSchool> pager){
        return schoolService.pager(pager);
    }







}
