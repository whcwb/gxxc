package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizExamPlace;
import com.cwb.platform.biz.service.ExamPlaceService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 考场信息
 * auther chenwei
 * create at 2018/6/19
 */
@RestController
@RequestMapping("api/examPlace")
public class ExamPlaceCtrl extends BaseController<BizExamPlace,String> {
    @Autowired
    private ExamPlaceService examPlaceService;
    @Override
    protected BaseService<BizExamPlace, String> getBaseService() {
        return examPlaceService;
    }
}
