package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizTrainPlaceMapper;
import com.cwb.platform.biz.model.BizDriversSchool;
import com.cwb.platform.biz.model.BizTrainPlace;
import com.cwb.platform.biz.service.SchoolService;
import com.cwb.platform.biz.service.TrainPlaceService;
import com.cwb.platform.biz.util.GpsUtil;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.common.Mapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TrainPlaceServiceImpl extends BaseServiceImpl<BizTrainPlace,String> implements TrainPlaceService {
    @Autowired
    private BizTrainPlaceMapper trainPlaceMapper;
    @Autowired
    private SchoolService schoolService;
    @Override
    protected Mapper<BizTrainPlace> getBaseMapper() {
        return trainPlaceMapper;
    }


    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        condition.setOrderByClause("create_time desc");
        return true;
    }

    @Override
    protected void afterPager(PageInfo<BizTrainPlace> resultPage) {
        List<BizTrainPlace> placeList = resultPage.getList();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String gps = request.getParameter("gps");
        if(StringUtils.isNotBlank(gps)){
            String[] gpses = gps.split(",");
            double latitude = Double.parseDouble(gpses[0]);  // 纬度
            double longitude = Double.parseDouble(gpses[1]); // 经度


            placeList.stream().forEach(bizTrainPlace -> {
                double distance = GpsUtil.getDistance(longitude, latitude, bizTrainPlace.getLongitude(), bizTrainPlace.getLatitude());
                bizTrainPlace.setDistance(distance);
            });
//            GpsUtil.getDistance(longitude,latitude)

        }else{
           placeList.stream().forEach(bizTrainPlace -> {
               bizTrainPlace.setDistance(null);
           });
        }


        super.afterPager(resultPage);
    }

    @Override
    public ApiResponse<String> validAndSave(BizTrainPlace entity){
        RuntimeCheck.ifBlank(entity.getSchoolCode(),"请选择驾校");
        BizDriversSchool school = schoolService.findById(entity.getSchoolCode());
        RuntimeCheck.ifNull(school,"未找到驾校");

        entity.setSchoolName(school.getSchoolName());
        entity.setPlaceId(genId());
        entity.setCreateTime(DateUtils.getNowTime());
        trainPlaceMapper.insertSelective(entity);
        return ApiResponse.success();
    }
}
