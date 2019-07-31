package com.cwb.platform.biz.controller;

import com.cwb.platform.biz.model.BizMsg;
import com.cwb.platform.biz.service.MsgService;
import com.cwb.platform.sys.base.BaseController;
import com.cwb.platform.sys.base.BaseService;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/msg")
public class MsgController extends BaseController<BizMsg,String> {
    @Autowired
    private MsgService service;

    @Override
    protected BaseService<BizMsg, String> getBaseService() {
        return service;
    }

    @GetMapping("/getUserList")
    public ApiResponse<String> getUserList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "8") int pageSize,@RequestParam(defaultValue = "0") String type){
        return service.getUserList(pageNum,pageSize,type);
    }

    @GetMapping("/getUserMsg")
    public ApiResponse<String> getUserMsgs(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "8") int pageSize,String userId){
        return service.getUserMsgs(pageNum,pageSize, userId);
    }

    @RequestMapping(value = "/reply",method = {RequestMethod.GET,RequestMethod.POST})
    public ApiResponse<String> replyUser(String userId, String content){
        return service.replyUser(userId, content);
    }

}
