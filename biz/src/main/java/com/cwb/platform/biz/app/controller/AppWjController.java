package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.model.BizWj;
import com.cwb.platform.biz.service.PtyhService;
import com.cwb.platform.biz.service.WjService;
import com.cwb.platform.sys.model.BizPtyh;
import com.cwb.platform.util.bean.ApiResponse;
import com.cwb.platform.util.exception.RuntimeCheck;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/wj")
public class AppWjController extends AppUserBaseController {
    @Autowired
    private WjService service;
    @Autowired
    private PtyhService ptyhService;

    /**
     * 根据对象字段值查询数据
     * @return
     */
    @RequestMapping(value="/getCondition", method={RequestMethod.POST})
    public ApiResponse<List<Boolean>> getCondition(){
        BizPtyh bizPtyh= getAppCurrentUser();

        List<Boolean> retlise=new ArrayList<Boolean>();

        BizPtyh ptyh= ptyhService.findById(bizPtyh.getId());
        String yhZt=ptyh.getYhZt();
        if(StringUtils.equals("2",yhZt)){
            retlise.add(false);
            retlise.add(false);
            retlise.add(false);
            retlise.add(false);
            return ApiResponse.success(retlise);
        }
        RuntimeCheck.ifNull(ptyh,"当前登录用户未空！");
        BizWj entity=new BizWj();
        entity.setYhId(bizPtyh.getId());
        entity.setWjSfyx("1");
        List<BizWj> list=service.findByEntity(entity);
        Map<String,String> bizWjMap=new HashMap<String,String>();

        if(list!=null&&list.size()>0){
            for(BizWj l:list){
                l.setWjTpdz("");//将文件地址给去掉
                String wjSxKey=l.getWjSx();//文件属性 10、 身份证正面 11、 身份证反面  20、 驾照正面 21、 驾照背面
                if(StringUtils.equals(l.getWjSx(),"10")){
                    wjSxKey="0";
                }else if(StringUtils.equals(l.getWjSx(),"11")){
                    wjSxKey="1";
                }else if(StringUtils.equals(l.getWjSx(),"20")){
                    wjSxKey="2";
                }else if(StringUtils.equals(l.getWjSx(),"21")){
                    wjSxKey="3";
                }
                bizWjMap.put(wjSxKey,l.getWjSx());
            }
        }
        for(int i=0;i<4;i++){
            String wjsx=bizWjMap.get(i+"");
            Boolean wjzt=false;
            if(StringUtils.isNotEmpty(wjsx)){
                wjzt=true;
            }
            retlise.add(wjzt);
        }
        return ApiResponse.success(retlise);
    }
}
