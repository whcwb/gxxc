package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.app.service.AppTxService;
import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.biz.service.TxService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/tx")
public class AppTxController extends AppUserBaseController {
    @Autowired
    private TxService service;
    @Autowired
    private AppTxService appTxService;

    /**
     * 用户提现操作
     * @return
     */
   @PostMapping("/save")
   public ApiResponse<String> save(@RequestParam("ttje") String ttJe,@RequestParam("yhkh") String yhkh,
                                   @RequestParam("khh") String khh,@RequestParam("txxm") String txXm, @RequestParam("ttfs")String ttFs){
       RuntimeCheck.ifTrue(StringUtils.isEmpty(ttJe),"您好，提现金额不能为空！");
       Double ttje= Double.parseDouble(ttJe);
       RuntimeCheck.ifFalse(ttje!=null && ttje>0,"您好，提现金额不能小于0！");
       BizPtyh  user=getAppCurrentUser();
       return service.saveUserDraw(ttje, yhkh, khh, txXm,ttFs,user);
   }

    /**
     * 按全部、已付款、待付款来查询自己对应的一级，二级佣金订单
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<BizTx>> pager(BizTx entity, Page<BizTx> pager){
//        RuntimeCheck.ifNull(user,"用户不存在");
        return appTxService.pager(pager);
    }
}
