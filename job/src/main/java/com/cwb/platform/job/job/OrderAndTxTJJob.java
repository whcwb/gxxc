package com.cwb.platform.job.job;

import com.cwb.platform.job.util.HttpUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;

public class OrderAndTxTJJob implements Job {

    @Value("${thUrl}")
    private String tjUrl;


    /**
     * 订单和提现统计job
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        HttpUtil.post(tjUrl);
    }
}
