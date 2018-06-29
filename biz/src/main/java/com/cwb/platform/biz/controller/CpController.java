package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.biz.service.CpService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 产品管理
 *
 */
@RestController
@RequestMapping("/api/cp")
public class CpController extends QueryController<BizCp,String> {
    @Autowired
    private CpService service;

    @Override
    protected BaseService<BizCp, String> getBaseService() {
        return service;
    }
    /**
     * 新增产品
     * @param entity
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
        public ApiResponse<String> save(BizCp entity){
            return service.saveCp(entity);
        }
    /**
     * 获取产品类型
     * @param cpType  产品类型（1、学费  2、补考费）必填
     *  1、通过产品类型，查询出有效的产品
     * @return
     */
    @RequestMapping(value="/getcplx", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<BizCp> getCpTyet(String cpType){
        //产品类型（1、学费  2、补考费）必填
        RuntimeCheck.ifBlank(cpType,"您好，请确定产品类型");
        return service.getCpTyet(cpType);
    }


    /**
     * 修改产品分佣费用 (需要生成短信验证码)
     */
    @PostMapping("/updateYj")
    public ApiResponse<String> updateYj(BizCp bizCp){
        return service.updateYj(bizCp);

    }






}
