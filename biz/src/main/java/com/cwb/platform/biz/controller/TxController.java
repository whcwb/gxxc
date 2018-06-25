package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.biz.service.TxService;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.sys.base.QueryController;
import com.cwb.platform.util.bean.ApiResponse;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tx")
//public class TxController {
public class TxController extends QueryController<BizTx,String> {
    @Autowired
    private TxService service;

    @Override
    protected BaseService<BizTx, String> getBaseService() {
        return service;
    }
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

    /**
     * 上传excel文件，批量导入体现
     */
    @PostMapping("/batchImport")
    public ApiResponse<List<String>> batchImport(String filePath) throws IOException {
        return service.batchImport(filePath);
    }

}
