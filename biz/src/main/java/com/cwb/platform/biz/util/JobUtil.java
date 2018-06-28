package com.cwb.platform.biz.util;

import com.cwb.platform.biz.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobUtil {
    @Autowired
    private JobService jobService;

    public void orderFulfilJob(){
        jobService.orderFulfilJob();
    }

}
