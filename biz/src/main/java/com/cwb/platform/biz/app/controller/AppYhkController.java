package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizYhk;
import com.cwb.platform.biz.service.YhkService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 银行卡管理
 *
 */
@RestController
@RequestMapping("/app/yhk")
public class AppYhkController extends AppUserBaseController {


    @Autowired
    private YhkService service;

    /**
     * 数据新增方法
     *
     * @param entity
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(BizYhk entity){
        BizPtyh user= getAppCurrentUser();
        RuntimeCheck.ifNull(entity,"入参不能为空");
        RuntimeCheck.ifNull(entity.getYhkKh(),"银行卡号不能为空");
        RuntimeCheck.ifNull(entity.getYhkXm(),"户主姓名不能为空");
        entity.setYhId(user.getId());
        return service.addUserCreditCard(entity);
    }


    /**
     * 数据修改方法
     *
     * @param entity
     * @return
     */
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(BizYhk entity){
        BizPtyh user= getAppCurrentUser();
        BizYhk obd=service.findById(entity.getId());
        RuntimeCheck.ifNull(obd,"该银行卡不存在");
        RuntimeCheck.ifFalse(StringUtils.equals(obd.getYhId(),user.getId()),"该卡查询异常，请刷新页面后重新重新尝试");

        return service.validAndUpdate(entity);
    }

    /**
     * 数据删除方法
     * 如果对数据要求高，请重写该方法或是不直接继承该类，防止数据泄露
     * @param id
     * @return
     */
    @RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
    public ApiResponse<String> remove(@PathVariable("pkid")String id){
        BizPtyh user= getAppCurrentUser();
        BizYhk obd=service.findById(id);
        RuntimeCheck.ifNull(obd,"该银行卡不存在");
        RuntimeCheck.ifFalse(StringUtils.equals(obd.getYhId(),user.getId()),"该卡查询异常，请刷新页面后重新重新尝试");
        service.remove(id);
        return ApiResponse.success();
    }
    /**
     * 根据对象字段值查询数据
     * @param entity
     * @return
     */
    @RequestMapping(value="/getCondition", method={RequestMethod.POST})
    public ApiResponse<List<BizYhk>> getCondition(BizYhk entity){
        BizPtyh user= getAppCurrentUser();
        entity.setYhId(user.getId());
        List<BizYhk> list=service.getList(entity);
        return ApiResponse.success(list);
    }
}
