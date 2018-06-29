package com.cwb.platform.job.job;


import com.cwb.platform.job.util.HttpUtil;
import com.cwb.platform.job.util.MD5Util;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/*// 在成功执行了job类的execute方法后,更新JobDetail中JobDataMap的数据
@PersistJobDataAfterExecution
// 等待上一次任务执行完成，才会继续执行新的任务
@DisallowConcurrentExecution*/
public class OrderFulfillJob implements Job {


    Logger logger = LoggerFactory.getLogger("access_info");

    // http://xclm.xxpt123.com:8080/biz/job/orderFulfil


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("564564987987894897466545649874987");
        // 生成密钥
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String format = dateTimeFormatter.format(localDateTime);

        Map<String,String> map  = new HashMap<>();
        try {
            map.put("entity",MD5Util.MD5Encode("123456789" + format , null ));
            //String post = HttpUtil.post("http://xclm.xxpt123.com:8080/biz/job/orderFulfil",map);
            String post = HttpUtil.post("http://localhost:9086/job/orderFulfil",map);
            logger.debug(post);
            //System.out.println(post);
            //System.out.println("--------------------");
        } catch (Exception e) {
            e.printStackTrace();
            JobExecutionException e2 = new JobExecutionException(e);
            // 当任务执行失败时，立即停止所有相关这个任务的触发器
            e2.setRefireImmediately(true);
        }
    }

/*
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String format = dateTimeFormatter.format(localDateTime);
        System.out.println(MD5Util.MD5Encode("123456789" + format,null));

    }*/


   /* @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }*/
}
