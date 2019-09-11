package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.model.BizTrainPlace;
import com.cwb.platform.biz.service.TrainPlaceService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/trainplace")
public class AppTrainPlaceController extends QueryController<BizTrainPlace,String> {
    @Autowired
    private TrainPlaceService placeService;

    @Override
    protected BaseService<BizTrainPlace, String> getBaseService() {
        return placeService;
    }




}
