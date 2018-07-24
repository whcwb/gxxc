package com.cwb.platform.biz.service.impl;

import com.cwb.platform.biz.mapper.BizExamPlaceMapper;
import com.cwb.platform.biz.model.BizExamPlace;
import com.cwb.platform.biz.service.ExamPlaceService;
import com.cwb.platform.biz.util.GpsUtil;
import com.cwb.platform.sys.base.BaseServiceImpl;
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

/**
 * auther chenwei
 * create at 2018/6/19
 */
@Service
public class ExamServiceImpl extends BaseServiceImpl<BizExamPlace,String> implements ExamPlaceService {
    @Autowired
    private BizExamPlaceMapper examPlaceMapper;
    @Override
    protected Mapper<BizExamPlace> getBaseMapper() {
        return examPlaceMapper;
    }

    @Override
    public ApiResponse<String> validAndSave(BizExamPlace entity){
        entity.setId(genId());
        entity.setCjsj(DateUtils.getNowTime());
        examPlaceMapper.insertSelective(entity);
        return ApiResponse.success();
    }

    @Override
    protected void afterPager(PageInfo<BizExamPlace> resultPage) {
        List<BizExamPlace> placeList = resultPage.getList();
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
