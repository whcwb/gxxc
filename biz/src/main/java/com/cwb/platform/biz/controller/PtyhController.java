package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ptyh")
public class PtyhController extends BaseController<BizPtyh, java.lang.String> {
    @Autowired
    private PtyhService service;

    @Override
    protected BaseService<BizPtyh, java.lang.String> getBaseService() {
        return service;
    }

    @RequestMapping(value = "/save", method = {RequestMethod.POST})
    public ApiResponse<String> save(BizPtyh entity) {
        return null;
    }

    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ApiResponse<String> update(BizPtyh entity) {
        return null;
    }

    @RequestMapping(value = "/remove/{pkid}", method = {RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid") String id) {
        return null;
    }

    @RequestMapping(value = "/removeIds", method = {RequestMethod.POST})
    public ApiResponse<String> remove(String[] ids) {
        return null;
    }

    @RequestMapping(value="/{pkid}", method={RequestMethod.GET})
    public ApiResponse<BizPtyh> get(@PathVariable("pkid")String pkid){
        return null;
    }
    @RequestMapping(value="/getAll", method={RequestMethod.GET})
    public ApiResponse<List<BizPtyh>> getAll(){
        return null;
    }

    @RequestMapping(value="/query", method={RequestMethod.GET})
    public ApiResponse<List<BizPtyh>> query(BizPtyh entity){
        return null;
    }

    @RequestMapping(value="/getCondition", method={RequestMethod.POST})
    public ApiResponse<List<BizPtyh>> getCondition(BizPtyh entity){
        return null;
    }

    /**
     * 更新用户是否锁定状态 0 否 1 是
     *
     * @param bizPtyh
     * @return
     */
    @PostMapping("/updateSfsd")
    public ApiResponse<String> updateSfsd(BizPtyh bizPtyh) {
        return service.updateSfsd(bizPtyh);
    }

    /**
     * 更新用户是否分配信息
     *  这个接口可能被放弃掉了
     * @param bizPtyh
     * @return
     */
    @PostMapping("/updateSffp")
    public ApiResponse<String> updateSffp(BizPtyh bizPtyh) {
        return service.updateSffp(bizPtyh);
    }

    /**
     * 更新用户认证状态
     *
     * @param bizPtyh
     * @return
     */
    @PostMapping("/updateyhrz")
    public ApiResponse<String> updateYhRz(BizPtyh bizPtyh) {
        return service.updateYhRz(bizPtyh);
    }

    /**
     * 根据条件查询已实名的教练
     * 这个接口让前台去分页去查
     */
    @PostMapping("/getCoaches")
    public ApiResponse<List<BizPtyh>> getCoaches(String name, String phone, String area,@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize) {
        return service.getCoaches(name, phone, area, pageNum, pageSize);
    }

    /**
     * 分配学员接口
     * 1
     */
    @PostMapping("/assignStudents")
    public ApiResponse<List<String>> assignStudents(@RequestParam(name = "yhIds") String yhIds,@RequestParam(name = "jlid") String jlId){
        return service.assignStudents(yhIds, jlId);

    }






}
