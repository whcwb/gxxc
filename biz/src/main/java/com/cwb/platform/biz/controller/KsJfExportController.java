package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizKsJf;
import com.cwb.platform.biz.service.KsjfService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.ExcelUtil;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 学员考试缴费记录表
 * Created by Administrator on 2018/6/19.
 */
@Controller
@RequestMapping("/pub/ksjf")
public class KsJfExportController extends QueryController<BizKsJf,String> {

    @Autowired
    private KsjfService service;

    @Override
    protected BaseService<BizKsJf, String> getBaseService() {
        return service;
    }


    private String getFileName(){
        try {
            return java.net.URLEncoder.encode(DateFormatUtils.format(new Date(),"yyyyMMddHHmmss") + ".xls","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping("export")
    public void export(Integer km, HttpServletResponse response) throws IOException {
        response.setContentType("application/msexcel");
        response.setHeader("pragma", "no-cache");
        response.addHeader("Content-Disposition","attachment; filename="+getFileName());
        OutputStream out = response.getOutputStream();
        String[] heads = new String[]{"姓名","身份证号","科目","是否缴费","缴费金额","缴费方式"};
        ExcelUtil.createSheet(out,"统计",heads,service.export(km));
    }

}
