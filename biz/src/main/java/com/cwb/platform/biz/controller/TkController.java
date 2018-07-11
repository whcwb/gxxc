package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizTk;
import com.cwb.platform.biz.service.TkService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 退款管理
 *
 */
@RestController
@RequestMapping("/api/tk")
public class TkController extends QueryController<BizTk,String> {
    @Autowired
    private TkService service;

    @Override
    protected BaseService<BizTk, String> getBaseService() {
        return service;
    }

    /**
     * 退款查询列表
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizTk>> pager(BizTk entity, Page<BizTk> pager){
        return service.pager(pager);
    }

    /**
     * 新增退款
     * @param entity
     * @return
     */
    @RequestMapping(value="/tk", method={RequestMethod.POST})
    public ApiResponse<String> updateTkType(BizTk entity){
        return service.updateTkType(entity);
    }

}
