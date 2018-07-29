package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizDriversSchoolMapper;
import com.cwb.platform.biz.model.BizDriversSchool;
import com.cwb.platform.biz.service.SchoolService;
import com.cwb.platform.biz.util.GpsUtil;
import com.cwb.platform.sys.base.BaseServiceImpl;
import com.cwb.platform.sys.base.LimitedCondition;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
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
public class SchoolServiceImpl extends BaseServiceImpl<BizDriversSchool,String> implements SchoolService {
    @Autowired
    private BizDriversSchoolMapper schoolMapper;
    @Override
    protected Mapper<BizDriversSchool> getBaseMapper() {
        return schoolMapper;
    }

    @Override
    public boolean fillPagerCondition(LimitedCondition condition){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()) .getRequest();
        String regionCode = request.getParameter("inputRegionCode");//区县行政区划代码
        if(StringUtils.isNotEmpty(regionCode)){
            condition.startWith(BizDriversSchool.InnerColumn.regionCode.name(), regionCode + "%");
        }

        condition.setOrderByClause("create_time desc");
        return true;
    }

    @Override
    public ApiResponse<String> validAndSave(BizDriversSchool school){
        school.setSchoolCode(genId());
        school.setCreateTime(DateUtils.getNowTime());

        schoolMapper.insertSelective(school);
        return ApiResponse.success();
    }

    @Override
    protected void afterPager(PageInfo<BizDriversSchool> resultPage) {
        List<BizDriversSchool> placeList = resultPage.getList();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String gps = request.getParameter("gps");
        if(StringUtils.isNotBlank(gps)){
            String[] gpses = gps.split(",");
            double latitude = Double.parseDouble(gpses[1]);  // 纬度
            double longitude = Double.parseDouble(gpses[0]); // 经度

            placeList.stream().forEach(bizTrainPlace -> {
                if(bizTrainPlace.getLat()!=null&&bizTrainPlace.getLng()!=null){
                    double distance = GpsUtil.getDistance(longitude, latitude, bizTrainPlace.getLat(), bizTrainPlace.getLng());
                    bizTrainPlace.setDistance(distance);
                }else{
                    bizTrainPlace.setDistance(null);
                }
            });
//            GpsUtil.getDistance(longitude,latitude)

        }else{
            placeList.stream().forEach(bizTrainPlace -> {
                bizTrainPlace.setDistance(null);
            });
        }


        super.afterPager(resultPage);
    }
}
