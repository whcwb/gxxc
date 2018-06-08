package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/wj")
public class AppWjController extends AppUserBaseController {
    @Autowired
    private WjService service;


    /**
     * 根据对象字段值查询数据
     * @return
     */
    @RequestMapping(value="/getCondition", method={RequestMethod.POST})
    public ApiResponse<List<BizWj>> getCondition(){
        BizPtyh bizPtyh= getAppCurrentUser();
        BizWj entity=new BizWj();
        entity.setYhId(bizPtyh.getId());
        entity.setWjSfyx("1");
        List<BizWj> list=service.findByEntity(entity);
        if(list!=null&&list.size()>0){
            for(BizWj l:list){
                l.setWjTpdz("");
            }
        }
        return ApiResponse.success(list);
    }
}
