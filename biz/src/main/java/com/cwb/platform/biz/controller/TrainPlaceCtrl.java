package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizTrainPlace;
import com.cwb.platform.biz.service.TrainPlaceService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/trainPlace")
public class TrainPlaceCtrl extends BaseController<BizTrainPlace,String> {
    @Autowired
    private TrainPlaceService trainPlaceService;
    @Override
    protected BaseService<BizTrainPlace, String> getBaseService() {
        return trainPlaceService;
    }
}
