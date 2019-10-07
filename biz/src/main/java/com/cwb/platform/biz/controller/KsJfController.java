package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizKsJf;
import com.cwb.platform.biz.service.KsjfService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.bean.ExcelParams;
import com.cwb.platform.util.commonUtil.DateUtils;
import com.cwb.platform.util.commonUtil.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 学员考试缴费记录表
 * Created by Administrator on 2018/6/19.
 */
@RestController
@RequestMapping("/api/ksjf")
public class KsJfController extends QueryController<BizKsJf,String> {

    @Autowired
    private KsjfService service;

    @Override
    protected BaseService<BizKsJf, String> getBaseService() {
        return service;
    }


    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizKsJf entity){
        return service.validAndSave(entity);
    }
    @RequestMapping("getPayInfo")
    public ApiResponse<Map<String,String>> getPayInfo(String yhId){
        return service.getPayInfo(yhId);
    }

    @RequestMapping("waitPaymentList")
    public ApiResponse<List<BizPtyh>> waitPaymentList(Integer km){
        return service.waitPaymentList(km);
    }

    @RequestMapping("batchImport")
    public ApiResponse<List<String>> batchImport(String filePath){
        return service.batchImport(filePath);
    }
    @PostMapping("/waitPaymentListNew")
    public ApiResponse<String> waitPaymentList(String xm, String phone, String km, String idCard, @RequestParam(defaultValue = "1") int pageNum , @RequestParam(defaultValue = "8") int pageSize){
        return service.waitPaymentListNew(xm,phone,km,idCard,pageNum,pageSize);
    }

    /**
     * 缴费记录撤回操作
     */
    public ApiResponse<String> updateRe(String id){
        return service.updateRe(id);
    }

}
