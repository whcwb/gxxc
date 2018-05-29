package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizCp;
import com.cwb.platform.biz.model.BizJl;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ptyh")
public class PtyhController extends BaseController<BizPtyh,java.lang.String>{
    @Autowired
    private PtyhService service;

    @Override
    protected BaseService<BizPtyh, java.lang.String> getBaseService() {
        return service;
    }

    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizPtyh entity){
        return null;
    }

    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(BizPtyh entity){
        return null;
    }

    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        return null;
    }

    @RequestMapping(value="/removeIds", method={RequestMethod.POST})
    public ApiResponse<String> remove(String[] ids){
        return null;
    }

    /**
     *  更新用户是否锁定状态 0 否 1 是
     * @param bizPtyh
     * @return
     */
    @PostMapping("/updateSfsd")
    public ApiResponse<String> updateSfsd(BizPtyh bizPtyh){
        return service.updateSfsd(bizPtyh);
    }

    /**
     * 更新用户是否分配信息
     * @param bizPtyh
     * @return
     */
    @PostMapping("/updateSffp")
    public ApiResponse<String> updateSffp(BizPtyh bizPtyh){
        return service.updateSffp(bizPtyh);
    }
    /**
     * 更新用户认证状态
     * @param bizPtyh
     * @return
     */
    @PostMapping("/updateyhrz")
    public ApiResponse<String> updateYhRz(BizPtyh bizPtyh){
        return service.updateYhRz(bizPtyh);
    }





}
