package com.cwb.platform.biz.app.controller;

import com.cwb.platform.biz.app.AppUserBaseController;
import com.cwb.platform.biz.service.MsgService;
import com.cwb.platform.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/msg")
public class AppMsgController  extends AppUserBaseController {

    @Autowired
    private MsgService service;

    /**
     * 获取用户聊天记录    分页
     */
    @GetMapping("/getUserMsg")
    public ApiResponse<String> getUserMsg(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "8") int pageSize){
        return service.getUserMsg(pageNum, pageSize);
    }

    /**
     * 用户添加回复
     */
    @PostMapping("/reply")
    public ApiResponse<String> reply(String content){
        return service.reply(content);
    }





}
