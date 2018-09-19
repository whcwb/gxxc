package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.app.service.AppTxService;
import com.cwb.platform.biz.model.BizTx;
import com.cwb.platform.biz.model.BizYhk;
import com.cwb.platform.biz.service.TxService;
import com.cwb.platform.biz.service.YhkService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import com.github.pagehelper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/tx")
public class AppTxController extends AppUserBaseController {
    @Autowired
    private TxService service;
    @Autowired
    private AppTxService appTxService;

    @Autowired
    private YhkService yhkService;


    /**
     * 用户提现操作
     * @param ttje 提现金额
     * @param yhkid 银行卡ID
     * @param ttfs  1、微信提现  2、银行卡提现 3、人工提现
     * @return
     */
   @RequestMapping(value="/save", method={RequestMethod.POST})
   public ApiResponse<String> save(String ttje, String yhkid,String ttfs){
       BizPtyh  user=getAppCurrentUser();

       RuntimeCheck.ifTrue(StringUtils.isEmpty(ttje),"您好，提现金额不能为空！");
       Double ttJe= Double.parseDouble(ttje);
       RuntimeCheck.ifFalse(ttje!=null && ttJe>0,"您好，提现金额不能小于0！");

       BizYhk bizYhk=new BizYhk();
       if(StringUtils.equals(ttfs,"2")){
           RuntimeCheck.ifTrue(StringUtils.isEmpty(yhkid),"您好，请选择银行卡！");
           bizYhk=yhkService.findById(yhkid);
           RuntimeCheck.ifNull(bizYhk,"您好，请选择银行卡！");
       }else if(StringUtils.equals(ttfs,"1")){
           RuntimeCheck.ifFalse( ttJe>=1 && ttJe<=800*100,"您好，微信提现的限额为[1-800]元");
           String openId=user.getYhOpenId();
           RuntimeCheck.ifNull(openId,"您好，当前账户无法使用微信提现！");
       }else if(StringUtils.equals(ttfs,"3")){
           //人工打电话去提现
       }else{
           RuntimeCheck.ifTrue(true,"无效的提现类型");
       }

       return service.saveUserDraw(ttJe, yhkid,bizYhk,user,ttfs);
   }

    /**
     * 按全部、已付款、待付款来查询自己对应的一级，二级提现明细
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
