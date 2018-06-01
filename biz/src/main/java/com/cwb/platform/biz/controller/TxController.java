package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.biz.service.TxService;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tx")
public class TxController {
//public class TxController extends BaseController<BizTx,java.lang.String>{
    @Autowired
    private TxService service;

//    @Override
//    protected BaseService<BizTx, java.lang.String> getBaseService() {
//        return service;
//    }
@RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
public ApiResponse<List<BizTx>> pager(BizTx entity, Page<BizTx> pager){
    return service.pager(pager);
}
    /**
     * 更新审核状态
     * @return
     */
   @PostMapping("/updateShzt")
    public ApiResponse<String> updateShzt(BizTx bizTx){
        return service.updateShzt(bizTx);
   }

   @PostMapping("/updateTxzt")
    public ApiResponse<String> updateTxzt(BizTx bizTx){
       return service.updateTxzt(bizTx);
   }



}
